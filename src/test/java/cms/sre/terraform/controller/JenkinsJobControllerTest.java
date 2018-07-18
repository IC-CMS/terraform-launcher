package cms.sre.terraform.controller;

import cms.sre.terraform.TestConfiguration;
import cms.sre.terraform.manager.JenkinsJobRunner;
import cms.sre.terraform.service.JenkinsJobService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfiguration.class,
        JenkinsJobController.class,
        JenkinsJobService.class,
        JenkinsJobRunner.class})
@AutoConfigureMockMvc
public class JenkinsJobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JenkinsJobController jenkinsJobController;

    @Test
    public void runJobEventTest() {

        StringBuilder stringBuilder = new StringBuilder();

        String appPath = System.getProperty("user.dir");


        Path path = Paths.get(appPath + "/src/test/resources/scripts/mock-run-jenkins-job.sh");

        System.out.println("Script Path:" + path);

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

            MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/jennkinsjob/run")
                    .content("{\"object_kind\": \"push\", \"unknown_prop\":\"Helloworld\"}");

            System.out.println(this.mockMvc.perform(post).andReturn().getResponse().getContentAsString());

            String response = this.mockMvc.perform(post)
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            System.out.println(response);


        } catch (Exception e) {
            e.printStackTrace();
        }

        // TODO Need to add an assert

    }

}
