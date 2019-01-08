package cms.sre.terraform.service;

import cms.sre.dna_common_data_model.emailnotifier.SendEmailRequest;
import cms.sre.terraform.config.AppConfig;
import cms.sre.terraform.manager.JenkinsJobRunner;
import cms.sre.terraform.manager.TerraformRunner;
import cms.sre.terraform.model.JenkinsJobResult;
import cms.sre.terraform.model.TerraformDestroyEvent;
import cms.sre.terraform.model.TerraformResult;
import cms.sre.terraform.model.WebHookMicroServiceEvent;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * This Service is for running Terraform commands based on input received from a
 * Gitlab Web Hook.
 */
@Service
public class TerraformService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    AppConfig appConfig;

	@Autowired
	private TerraformRunner terraformRunner;

	@Autowired
	private JenkinsStatusService jenkinsStatusService;

	@Autowired
	private JenkinsJobRunner jenkinsJobRunner;

	@Autowired
    EmailNotifierService emailNotifierService;

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

			if (terraformResult.getStatus() != null && !terraformResult.getStatus().equals("Failed")) {

				// Before launching build, ensure the jenkins server is up all the way
				// and ready to accepts jobs

				boolean readyToBuild = false;

				String port;
				String host;

				if (appConfig.getEnvironment().equals("development")) {

				    port = appConfig.getJenkinsPort();
				    host = appConfig.getJenkinsHost();
                }
                else {
                    port = "8080";
                    host = terraformResult.getHost();
                }

				// TODO need to decide how long to try before giving up

                while(!readyToBuild) {

				    if (jenkinsStatusService.checkStatus(host, port) == 200) {
				        readyToBuild = true;
                    }

					try {
						sleep(5000);
					} catch (InterruptedException e) {

						logger.error("Status check failed for Jenkins Server: " + e.getMessage());
					}

				}

				if (readyToBuild) {

				    String jenkinsUrl = host + ":" + "8089";

					JenkinsJobResult result =
							jenkinsJobRunner.run(jenkinsUrl, event.getSsl_url(), event.getBranch_name(), event.getProject_name());

					if (result == null || result.getStatus() == null || result.getStatus().equals("Failed")) {

					    logger.error("Something went wrong");

                    } else {

					    logger.info("Checking build status");

						JenkinsServer jenkins = null;

						try {

							jenkins = new JenkinsServer(new URI(jenkinsUrl), appConfig.getJenkinsUsername(), appConfig.getJenkinsPassword());

                            //CrumbJson crumJson = CrumbRetriever.retrieveCrumb(jenkinsUrl, appConfig.getJenkinsUsername(), appConfig.getJenkinsPassword());

						try {

						    boolean isBuilding = true;

						    BuildWithDetails details = null;

						    while(isBuilding) {

                                try {
                                    sleep(5000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                Map<String, Job> jobs = jenkins.getJobs();

                                if (jobs != null && jobs.get(event.getProject_name()) != null ) {

                                    JobWithDetails job = jobs.get(event.getProject_name()).details();

                                    details = job.getLastBuild().details();

                                    isBuilding = details.isBuilding();

                                }
                                else {
                                    isBuilding = false;
                                }
                            }

                            if (details != null) {

                                String results = details.getConsoleOutputText();

                                logger.info(results);

                                BuildResult buildResult = details.getResult();

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                if("SUCCESS".equals(buildResult.name())) {

                                    SendEmailRequest emailRequest = new SendEmailRequest();
                                    emailRequest.setSubject("Jenkins Job Build status: " + event.getProject_name() + " Built Successfully");
                                    emailRequest.setDn("CN=Kiin Do Vah ermoffa, OU=Whiterun, OU=Breezehome, OU=Empire, O=JarlBalgruuf, C=Tamriel");
                                    emailRequest.setBody(
                                            "This is an automatically generated email - Do not reply to this email \r\n" +
                                            "Jenkins successfully completed build of project: \r\n" +
                                            event.getSsl_url() + " at " + sdf.format(new Date(details.getTimestamp())) + "\r\n");

                                    emailNotifierService.sendEmail(emailRequest);

                                } else {

                                    SendEmailRequest emailRequest = new SendEmailRequest();
                                    emailRequest.setSubject("Jenkins Job Build status: " + event.getProject_name() + " Build Failure");
                                    emailRequest.setDn("CN=Kiin Do Vah ermoffa, OU=Whiterun, OU=Breezehome, OU=Empire, O=JarlBalgruuf, C=Tamriel");
                                    emailRequest.setBody(
                                            "This is an automatically generated email - Do not reply to this email \r\n" +
                                            "Jenkins build failed project: \r\n" +
                                            event.getSsl_url() + "at " + sdf.format(new Date(details.getTimestamp())) + "\r\n");

                                    emailNotifierService.sendEmail(emailRequest);
                                }

                                jenkins.deleteJob(event.getProject_name(), true);

                            }
						} catch (IOException e) {
							e.printStackTrace();
						}
						} catch (URISyntaxException e) {
							e.printStackTrace();
						} catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

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