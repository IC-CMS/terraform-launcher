package cms.sre.terraform.manager;

import cms.sre.terraform.model.JenkinsJobResult;
import cms.sre.terraform.service.JenkinsStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class JenkinsJobRunner extends ProcessRunner {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${app.jenkins_dev_job_runner_script}")
    private String jenkinsDevJobRunnerScript;

    @Autowired
    private JenkinsStatusService jenkinsStatusService;

    public JenkinsJobResult run(String jenkinsServerURL, String gitRepository, String jobName) {

        // Before launching build, ensure the jenkins server is up all the way
        // and ready to accepts jobs

        boolean readyToBuild = false;

        // TODO need to decide how long to try before giving up
        while (jenkinsStatusService.checkStatus(jenkinsServerURL) != 200) {

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

                logger.error(e.getMessage());
            }

            if (jenkinsStatusService.checkStatus(jenkinsServerURL) == 200) {
                readyToBuild = true;
            }
        }

        JenkinsJobResult jenkinsJobResult = null;

        // Jenkins Server should be up and ready to run jobs
        if (readyToBuild) {

            logger.debug("Executing script: " + jenkinsDevJobRunnerScript);

            BufferedReader stdInput = runProcess(jenkinsDevJobRunnerScript);

            String input = null;

            try {

                while ((input = stdInput.readLine()) != null) {

                    //logger.debug(input);

                    if (input.startsWith("Apply")) {

                        jenkinsJobResult = captureProcessingResults(input);
                    }

                }

            } catch (IOException ioe) {

                destroyProcess();

            }
        }

        logger.debug("Jenkins Job Result: " + jenkinsJobResult);

        destroyProcess();

        return jenkinsJobResult;
    }

    /**
     * Captures log processing results from Terraform commands
     * @param logCapture
     * @return
     */
    private JenkinsJobResult captureProcessingResults(String logCapture) {

        JenkinsJobResult jenkinsJobResult = null;

        if (logCapture.startsWith("Apply")) {

            jenkinsJobResult= new JenkinsJobResult();

            String[] result = logCapture.split(" ");


        } else if (logCapture.startsWith("Destroy")) {


            String[] result = logCapture.split(" ");

        }

        logger.debug("Capture result: " + jenkinsJobResult);

        return jenkinsJobResult;
    }
}
