package cms.sre.terraform.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;


public class RestValidationJenkinsJobUtil {

    public static void main(String[] args) {

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {

            HttpPost request = new HttpPost("http://localhost:8080/jenkinsjob/run");
            StringEntity params = new StringEntity("{\"event_type\": \"build\", \"jenkins_server\":\"http://localhost:8080\", \"job_name\":\"docker-jenkins\", \"git_repository\":\"git@github.com:IC-CMS/docker-jenkins.git\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            System.out.println("HTTP RESPONSE: " + response.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}
