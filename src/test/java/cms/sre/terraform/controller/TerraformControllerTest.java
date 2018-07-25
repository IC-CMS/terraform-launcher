package cms.sre.terraform.controller;

import cms.sre.terraform.config.AppConfig;
import cms.sre.terraform.manager.JenkinsJobRunner;
import cms.sre.terraform.manager.TerraformRunner;
import cms.sre.terraform.service.JenkinsStatusService;
import cms.sre.terraform.service.TerraformService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        AppConfig.class,
        TerraformController.class,
        TerraformService.class,
        TerraformRunner.class,
        JenkinsStatusService.class,
        JenkinsJobRunner.class})
@AutoConfigureMockMvc
public class TerraformControllerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TerraformController terraformController;

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
