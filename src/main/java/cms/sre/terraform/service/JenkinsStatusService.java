package cms.sre.terraform.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JenkinsStatusService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Check status of the jenkins server
     * @param serverURL
     * @return Integer
     */
    public Integer checkStatus(String serverURL) {

        Integer status = null;

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {

            HttpPost request = new HttpPost(serverURL);
            HttpResponse response = httpClient.execute(request);

            status = response.getStatusLine().getStatusCode();

            logger.debug(serverURL + " " + status);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return status;

    }
}
