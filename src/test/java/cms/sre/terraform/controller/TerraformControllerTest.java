package cms.sre.terraform.controller;

import cms.sre.terraform.App;
import cms.sre.terraform.config.AppConfig;
import cms.sre.terraform.manager.JenkinsJobRunner;
import cms.sre.terraform.manager.TerraformRunner;
import cms.sre.terraform.service.JenkinsStatusService;
import cms.sre.terraform.service.TerraformService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.verification.LoggedRequest;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        App.class,
        AppConfig.class,
        TerraformController.class,
        TerraformService.class,
        TerraformRunner.class,
        JenkinsStatusService.class,
        JenkinsJobRunner.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TerraformControllerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().dynamicPort());

    private WireMockServer wireMockServer;

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TerraformController terraformController;

    /**
     * Performs preparations before each test.
     */
    @Before
    public void setup() {

        /*
         * Create the WireMock server to be used by a test.
         * This also ensures that the records of received requests kept by the WireMock
         * server and expected scenarios etc are cleared prior to each test.
         * An alternative is to create the WireMock server once before all the tests in
         * a test-class and call {@code resetAll} before each test.
         */
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
    }

    /**
     * Performs cleanup after each test.
     */
    @After
    public void tearDown() {
        /* Stop the WireMock server. */
        wireMockServer.stop();

        /*
         * Find all requests that were expected by the WireMock server but that were
         * not matched by any request actually made to the server.
         * Logs any such requests as errors.
         */
        final List<LoggedRequest> theUnmatchedRequests = wireMockServer.findAllUnmatchedRequests();
        if (!theUnmatchedRequests.isEmpty()) {
            logger.error("Unmatched requests: {}", theUnmatchedRequests);
        }
    }


    @Test
    public void applyEventTest() {

        StringBuilder stringBuilder = new StringBuilder();

        Path path = Paths.get("src/test/resources/gitlab_webhook_microservice.json");

        logger.info("Testfile path: " + path);

        if (!Files.exists(path)) {
            System.out.println("File not found!");
            System.exit(-1);
        }

        try (Stream<String> input = Files.lines(path)) {
            input.forEach(stringBuilder::append);

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        wireMockServer.stubFor(get(urlEqualTo("/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withStatus(200)));

        wireMockServer.stubFor(get(urlEqualTo("/api/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody("{\"action\":\"Apply\",\"status\":\"Complete!\",\"resourcesAdded\":4,\"resourcesChanged\":0,\"resourcesDestroyed\":0,\"host\":null}")
                        .withStatus(200)));

        try {

            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/terraform/apply");
            request.contentType(MediaType.APPLICATION_JSON).
                content(stringBuilder.toString());

            String response = this.mockMvc.perform(request)
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            logger.info("Terraform Apply Request: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO Need to add an assert

    }

    @Test
    public void TerraformDestroyTest() throws Exception {

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/terraform/destroy")
                .content("{\"object_kind\": \"destroy\", \"server\":\"jenkins_dev\", \"project\":\"terraform-launcher\"}");
        String response = mockMvc.perform(post).andReturn().getResponse().getContentAsString();

        System.out.println("RESPONSE:" + response);

        // TODO Need to add an assert
    }


}
