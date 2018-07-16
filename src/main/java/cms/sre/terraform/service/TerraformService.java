package cms.sre.terraform.service;

import cms.sre.terraform.manager.TerraformRunner;
import cms.sre.terraform.model.GitlabPushEvent;
import cms.sre.terraform.model.TerraformDestroyEvent;
import cms.sre.terraform.model.TerraformResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerraformService {

    private static final Logger logger = LoggerFactory.getLogger("TerraformService.class");

    @Autowired
    private TerraformRunner terraformRunner;

    public TerraformResult processRequest(GitlabPushEvent event) {

        TerraformResult terraformResult = null;

        logger.debug("Event object kinds: " + event.getObject_kind());

        switch (event.getObject_kind()) {

            case "push":

                logger.info("Detected Git push event, launch jenkins instance");
                terraformResult = terraformRunner.apply();
                break;

            default:
                logger.info("Unidentified object kind, no action performed");
                break;

        }

        return terraformResult;

    }

    public TerraformResult processRequest(TerraformDestroyEvent event) {

        return terraformRunner.destroy();
    }
}
