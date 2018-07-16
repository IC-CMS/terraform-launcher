package cms.sre.terraform.manager;

import cms.sre.terraform.model.JenkinsJobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;

@Component
public class JenkinsJobRunner extends ProcessRunner {

    public static final Logger logger = LoggerFactory.getLogger("JenkinsJobRunner.class");

    @Value("${jenkins.dev.job.runner.script}")
    private String jenkinsDevJobRunnerScript;

    public JenkinsJobResult run() {

        logger.debug("Executing script: " + jenkinsDevJobRunnerScript);

        // Launch the jenkins-dev Instance first

        JenkinsJobResult jenkinsJobResult = null;

        BufferedReader stdInput = runProcess(jenkinsDevJobRunnerScript);

        String input = null;

        try {

            while ((input = stdInput.readLine()) != null) {

                //logger.debug(input);

                if (input.startsWith("Apply")) {

                    jenkinsJobResult = captureProcessingResults(input);
                }

            }

        } catch(IOException ioe) {

            destroyProcess();

        }

        logger.debug("Jenkins Job Result: " + jenkinsJobResult);

        // Make sure it's up and running

        // Execute a job

        // Destroy the jenkins-dev instance when build completed

        destroyProcess();

        return jenkinsJobResult;
    }

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
