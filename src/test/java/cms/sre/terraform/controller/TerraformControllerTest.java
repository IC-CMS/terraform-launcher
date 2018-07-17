package cms.sre.terraform.controller;

import cms.sre.terraform.TestConfiguration;
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
@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc
public class TerraformControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TerraformController terraformController;

    @Test
    public void applyEventTest() {

        StringBuilder stringBuilder = new StringBuilder();

        Path path = Paths.get("src/test/resources/gitlab_webhook.txt");

        System.out.println("path");

        if (!Files.exists(path)) {
            System.out.println("File not found!");
            System.exit(-1);
        }

        try (Stream<String> input = Files.lines(path)) {
            input.forEach(stringBuilder::append);

        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {

            HttpPost request = new HttpPost("http://localhost:8080/terraform/apply");
            StringEntity params = new StringEntity(stringBuilder.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            System.out.println(response.getStatusLine().getStatusCode());


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
