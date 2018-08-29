package cms.sre.terraform.service;

import cms.sre.terraform.config.AppConfig;
import com.offbytwo.jenkins.JenkinsServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;


public class JenkinsClientApiService {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppConfig appConfig;

    JenkinsServer jenkins;

    public void connect() {

        try (JenkinsServer jenkinsServer = jenkins = new JenkinsServer(new URI("http://localhost:8080/jenkins"),
                appConfig.getJenkinsUsername(),
                appConfig.getJenkinsPassword())) {

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void run() {


    }
}
