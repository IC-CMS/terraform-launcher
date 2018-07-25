package cms.sre.terraform.service;

import cms.sre.terraform.manager.JenkinsJobRunner;
import cms.sre.terraform.manager.TerraformRunner;
import cms.sre.terraform.model.WebHookMicroServiceEvent;
import cms.sre.terraform.model.TerraformDestroyEvent;
import cms.sre.terraform.model.TerraformResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This Service is for running Terraform commands based on input received from a
 * Gitlab Web Hook.
 */
@Service
public class TerraformService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TerraformRunner terraformRunner;

	@Autowired
	private JenkinsStatusService jenkinsStatusService;

	@Autowired
	private JenkinsJobRunner jenkinsJobRunner;

	/**
	 * Processes a GitLab WebHook Microservice Push Event request
	 * 
	 * @param event
	 * @return TerraformResult
	 */
	public TerraformResult processRequest(WebHookMicroServiceEvent event) {

		TerraformResult terraformResult = null;

		logger.debug("gitlab_webhook request: " + event.getProject_name() + " event=" + event.getObject_kind());

		switch (event.getObject_kind()) {

		case "push":

			logger.info("Detected Git push event, launching jenkins server instance");
			terraformResult = terraformRunner.apply();

			// Check if the terraform apply succeeded

			if (terraformResult.getStatus() != null && terraformResult.getHost() != null && !terraformResult.getStatus().equals("Failed")) {

				// Before launching build, ensure the jenkins server is up all the way
				// and ready to accepts jobs

				boolean readyToBuild = false;

				// TODO need to decide how long to try before giving up
				while (jenkinsStatusService.checkStatus(terraformResult.getHost()) != 200) {

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

						logger.error("Status check failed for Jenkins Server: " + e.getMessage());
					}

					if (jenkinsStatusService.checkStatus(terraformResult.getHost()) == 200) {
						readyToBuild = true;
					}
				}

				if (readyToBuild) {

					jenkinsJobRunner.run(terraformResult.getHost(), event.getSsl_url(), event.getProject_name());

				}
				
			} else {
				
				// Somehow report that there was an error, for now log it.
				logger.error("Terraform Apply Failed for " + event.getProject_name() + " event=" + event.getObject_kind());
			}

			break;

		default:
			logger.info("Unidentified object kind, no action performed");
			break;

		}

		return terraformResult;

	}

	/**
	 * Proccess a Terraform Destroy Event request
	 * 
	 * @param event
	 * @return TerraformResult
	 */
	public TerraformResult processRequest(TerraformDestroyEvent event) {

		return terraformRunner.destroy();
	}
}
