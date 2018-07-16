package cms.sre.terraform.manager;

import cms.sre.terraform.model.TerraformApplyResult;
import cms.sre.terraform.model.TerraformDestroyResult;
import cms.sre.terraform.model.TerraformResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class TerraformRunner extends ProcessRunner {

    public static final Logger logger = LoggerFactory.getLogger("TerraformRunner.class");

    @Value("${jenkins.dev.launch.script}")
    private String jenkinsDevLaunchScript;

    @Value("${jenkins.dev.destroy.script}")
    private String jenkinsDevDestroyScript;

    public TerraformRunner() {

    }

    public TerraformResult apply() {

        logger.debug("Executing script: " + jenkinsDevLaunchScript);

        // Launch the jenkins-dev Instance first

        TerraformResult terraformResult = null;

        BufferedReader stdInput = runProcess(jenkinsDevLaunchScript);

        String input = null;

        try {

            while ((input = stdInput.readLine()) != null) {

                //logger.debug(input);

                if (input.startsWith("Apply")) {

                    terraformResult = captureProcessingResults(input);
                }

            }

        } catch(IOException ioe) {

            destroyProcess();

        }

        logger.debug("Launch result: " + terraformResult);

        // Make sure it's up and running

        // Execute a job

        // Destroy the jenkins-dev instance when build completed

        destroyProcess();

        return terraformResult;
    }

    public TerraformResult destroy() {

        TerraformResult terraformResult = null;

        BufferedReader stdInput = runProcess(jenkinsDevDestroyScript);

        String input = null;

        try {

            while ((input = stdInput.readLine()) != null) {

                //logger.debug(input);

                if (input.startsWith("Destroy")) {

                    terraformResult = captureProcessingResults(input);
                }

            }
        } catch(IOException ioe) {

            ioe.printStackTrace();

            destroyProcess();
        }

        logger.debug("Destroy result: " + terraformResult);

        destroyProcess();

        return terraformResult;
    }

    public String plan() {

        return null;
    }


    private TerraformResult captureProcessingResults(String logCapture) {

        TerraformResult terraformResult = null;

        if (logCapture.startsWith("Apply")) {

            terraformResult = new TerraformApplyResult();

            String[] result = logCapture.split(" ");

            terraformResult.setAction(result[0]);
            terraformResult.setStatus(result[1]);
            terraformResult.setResourcesAdded(Integer.parseInt(result[3]));
            terraformResult.setResourcesChanged(Integer.parseInt(result[5]));
            terraformResult.setResourcesDestroyed(Integer.parseInt(result[7]));

        } else if (logCapture.startsWith("Destroy")) {

            terraformResult = new TerraformDestroyResult();
            String[] result = logCapture.split(" ");

            terraformResult.setAction(result[0]);
            terraformResult.setStatus(result[1]);
            terraformResult.setResourcesAdded(Integer.parseInt(result[3]));
        }

        logger.debug("Capture result: " + terraformResult);

        return terraformResult;
     }
}
