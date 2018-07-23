package cms.sre.terraform.service;

import cms.sre.terraform.manager.JenkinsJobRunner;
import cms.sre.terraform.model.JenkinsJobEvent;
import cms.sre.terraform.model.JenkinsJobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JenkinsJobService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JenkinsJobRunner jenkinsJobRunner;

    public JenkinsJobResult processRequest(JenkinsJobEvent event) {

        JenkinsJobResult jenkinsJobResult = null;

        logger.debug("Event type: " + event.getEvent_type());

        switch (event.getEvent_type()) {

            case "build":

                logger.info("Detected JenkinsBuild Request, starting build");
                jenkinsJobResult = jenkinsJobRunner.run(event.getJenkins_server(), event.getGit_repository(), event.getJob_name());
                break;

            default:
                logger.error("Unidentified object kind, no action performed");
                break;

        }

        return jenkinsJobResult;

    }

}
