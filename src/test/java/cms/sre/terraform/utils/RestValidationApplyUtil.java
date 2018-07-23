package cms.sre.terraform.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class RestValidationApplyUtil {

	private static final Logger logger = LoggerFactory.getLogger(RestValidationApplyUtil.class);
	
    public static void main(String[] args) {

        StringBuilder stringBuilder = new StringBuilder();
        
        String appPath = System.getProperty("user.dir");

        Path path = Paths.get(appPath + "/src/test/resources/gitlab_webhook_microservice.json");
        
        logger.debug("Path:" + path);

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

            HttpPost request = new HttpPost("http://localhost:8088/terraform/apply");
            StringEntity params = new StringEntity(stringBuilder.toString());
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            System.out.println(response.getStatusLine().getStatusCode());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
