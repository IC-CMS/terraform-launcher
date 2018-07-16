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

    private static final Logger logger = LoggerFactory.getLogger("JenkinsJobService.class");

    @Autowired
    private JenkinsJobRunner jenkinsJobRunner;

    public JenkinsJobResult processRequest(JenkinsJobEvent event) {

        JenkinsJobResult jenkinsJobResult = null;

        logger.debug("Event object kinds: " + event.getObject_kind());

        switch (event.getObject_kind()) {

            case "push":

                logger.info("Detected Git push event, launch jenkins instance");
                jenkinsJobResult = jenkinsJobRunner.run();
                break;

            default:
                logger.info("Unidentified object kind, no action performed");
                break;

        }

        return jenkinsJobResult;

    }

}
