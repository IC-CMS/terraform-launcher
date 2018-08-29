package cms.sre.terraform.util;

import cms.sre.terraform.model.CrumbJson;
import com.google.gson.Gson;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.BasicResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Simple utility for retrieving a crumb issued by Jenkins
 */
public class CrumbRetriever {

    private static final Logger logger = LoggerFactory.getLogger(CrumbRetriever.class);

    public static CrumbJson retrieveCrumb(String jenkinsUrl, String userName, String password) throws Exception {

        URI uri = new URI(jenkinsUrl);

        JenkinsHttpClient httpClient = new JenkinsHttpClient(uri, userName, password);

        String crumbResponse = httpClient.get("crumbIssuer/api/json");

        logger.debug("Crumb: " + crumbResponse);

        CrumbJson crumbJson = new Gson().fromJson(crumbResponse, CrumbJson.class);

        return crumbJson;
    }

    private static String toString(HttpClient client,
                            HttpRequestBase request) throws Exception {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String responseBody = client.execute(request, responseHandler);
        System.out.println(responseBody + "\n");
        return responseBody;
    }
}
