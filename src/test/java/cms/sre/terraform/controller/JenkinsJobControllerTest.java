package cms.sre.terraform.controller;

import cms.sre.terraform.config.AppConfig;
import cms.sre.terraform.manager.JenkinsJobRunner;
import cms.sre.terraform.service.JenkinsJobService;
import org.junit.Assert;
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
        JenkinsJobController.class,
        JenkinsJobService.class,
        JenkinsJobRunner.class})
@AutoConfigureMockMvc
public class JenkinsJobControllerTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JenkinsJobController jenkinsJobController;

    @Test
    public void runJobEventTest() {

        StringBuilder stringBuilder = new StringBuilder();

        String appPath = System.getProperty("user.dir");

        Path path = Paths.get(appPath + "/src/test/resources/jenkins_job_build.json");

        logger.info("WebHook Microservice:" + path);

        if (!Files.exists(path)) {
            System.out.println("File not found!");
            System.exit(-1);
        }

        try (Stream<String> input = Files.lines(path)) {
            input.forEach(stringBuilder::append);

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        String response = null;

        try {

            MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/jenkinsjob/run")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(stringBuilder.toString());

            logger.info(this.mockMvc.perform(post).andReturn().getResponse().getContentAsString());

            response = this.mockMvc.perform(post)
                    .andReturn()
                    .getResponse()
                    .getContentAsString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(response, "{\"action\":\"Apply\",\"status\":\"Complete!\",\"resourcesAdded\":4,\"resourcesChanged\":0,\"resourcesDestroyed\":0,\"host\":null}");

    }

    @Test
    public void runJobEventUnknownPropertiesTest() {

        StringBuilder stringBuilder = new StringBuilder();

        String appPath = System.getProperty("user.dir");

        Path path = Paths.get(appPath + "/src/test/resources/scripts/mock-run-jenkins-job.sh");

        logger.info("Script Path:" + path);

        if (!Files.exists(path)) {
            System.out.println("File not found!");
            System.exit(-1);
        }

        try (Stream<String> input = Files.lines(path)) {
            input.forEach(stringBuilder::append);

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        String response = null;

        try {

            MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/jenkinsjob/run")
                    .content("{\"event_type\": \"push\", \"unknown_prop\":\"Helloworld\"}");

            logger.info(this.mockMvc.perform(post).andReturn().getResponse().getContentAsString());

            response = this.mockMvc.perform(post)
                    .andReturn()
                    .getResponse()
                    .getContentAsString();


        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals(response, "Failed to launch job, or job result not returned");

    }

}
