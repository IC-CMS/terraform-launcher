package cms.sre.terraform.service;

import cms.sre.terraform.config.AppConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JenkinsStatusService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppConfig appConfig;

    /**
     * Check status of the jenkins server
     *
     * @param host
     * @return Integer
     */
    public Integer checkStatus(String host, String port) {

        ObjectMapper mapper = new ObjectMapper();

        Integer status = -1;
        String userName = appConfig.getJenkinsUsername();
        String password = appConfig.getJenkinsPassword();

        String jenkinsUrl = host + ":" + port;

        HttpClient httpClient = HttpClientBuilder.create().build();

        try {

            String apiKey = userName + ":" + password;

            HttpGet request = new HttpGet(jenkinsUrl);
            request.addHeader("Authorization", "Basic " + Base64.encode(apiKey.getBytes()));
            HttpResponse response = httpClient.execute(request);

            status =  response.getStatusLine().getStatusCode();
        } catch(IOException e) {
            logger.error(e.getMessage());
        }

        return status;

    }
}
