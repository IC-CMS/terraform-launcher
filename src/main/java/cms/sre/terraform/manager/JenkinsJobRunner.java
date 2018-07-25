package cms.sre.terraform.manager;

import cms.sre.terraform.config.AppConfig;
import cms.sre.terraform.model.JenkinsJobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class JenkinsJobRunner extends ProcessRunner {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AppConfig appConfig;

    /**
     * Run a Jenkins Job and return the build results
     *
     * @param host
     * @param gitRepository
     * @param jobName
     * @return JenkinsJobResult
     */
    public JenkinsJobResult run(String host, String gitRepository, String jobName) {

        logger.debug("App path: " + System.getProperty("user.dir"));

        JenkinsJobResult jenkinsJobResult = null;

        logger.debug("Executing script: " + appConfig.getJenkinsJobRunnerScript());

        logger.info("Running job on host: " + host + " against repo: " + gitRepository + " job: " + jobName);

        BufferedReader stdInput = runProcess(appConfig.getJenkinsJobRunnerScript(), host, gitRepository, jobName);

        String input = null;

        try {

            while ((input = stdInput.readLine()) != null) {

                logger.debug(input);

                if (input.startsWith("Apply")) {

                    jenkinsJobResult = captureProcessingResults(input);
                }
            }

        } catch (IOException ioe) {

            destroyProcess();

        }

        logger.debug("Jenkins Job Result: " + jenkinsJobResult);

        destroyProcess();

        return jenkinsJobResult;
    }

    /**
     * Captures log processing results from Terraform commands
     *
     * @param logCapture
     * @return
     */
    private JenkinsJobResult captureProcessingResults(String logCapture) {

        JenkinsJobResult jenkinsJobResult = null;

        if (logCapture.startsWith("Apply")) {

            jenkinsJobResult = new JenkinsJobResult();

            String[] result = logCapture.split(" ");

            jenkinsJobResult.setAction(result[0]);
            jenkinsJobResult.setStatus(result[1]);
            jenkinsJobResult.setResourcesAdded(Integer.parseInt(result[3]));
            jenkinsJobResult.setResourcesChanged(Integer.parseInt(result[5]));
            jenkinsJobResult.setResourcesDestroyed(Integer.parseInt(result[7]));


        } else if (logCapture.startsWith("Destroy")) {

            String[] result = logCapture.split(" ");

        }

        logger.debug("Capture result: " + jenkinsJobResult);

        return jenkinsJobResult;
    }
}
