package cms.sre.terraform.manager;

import java.io.BufferedReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cms.sre.terraform.model.TerraformApplyResult;
import cms.sre.terraform.model.TerraformDestroyResult;
import cms.sre.terraform.model.TerraformResult;

@Component
public class TerraformRunner extends ProcessRunner {

    public final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${app.jenkins_dev_launch_script}")
    private String jenkinsDevLaunchScript;

    @Value("${app.jenkins_dev_destroy_script}")
    private String jenkinsDevDestroyScript;
    
    @Value("${app.environment}")
    private String applicationEnvironment;

    public TerraformResult apply() {
    	
    	String scriptPath = null;
    	
    	if (applicationEnvironment.equals("development")) {
    		
    		String appPath = System.getProperty("user.dir");
    		
    	    scriptPath = appPath + "/" + jenkinsDevLaunchScript;
    		
        }
    	else {
    		scriptPath = jenkinsDevLaunchScript;
    	}

        logger.debug("Attempting to execute script: " + scriptPath);

        // Launch a jenkins instance

        TerraformResult terraformResult = null;

        BufferedReader stdInput = runProcess(scriptPath);

        String input = null;

        try {

            while ((input = stdInput.readLine()) != null) {

                logger.debug(input);

                if (input.startsWith("Apply")) {

                    terraformResult = captureProcessingResults(input);
                }

            }

        } catch(IOException ioe) {

            // Didn't capture log so return status as failed
            terraformResult = new TerraformApplyResult();
            terraformResult.setStatus("Failed");
            
            logger.error("Apply Failed: " + terraformResult.getHost());

            destroyProcess();

        }

        // If result is null return status as failed
        if (terraformResult == null) {

            terraformResult = new TerraformApplyResult();
            terraformResult.setStatus("Failed");
            
            logger.error("Apply Failed: " + terraformResult.getHost());

        }

        logger.debug("Launch result: " + terraformResult);

        destroyProcess();

        return terraformResult;
    }

    public TerraformResult destroy() {
    	
    	String scriptPath = null;
        if (applicationEnvironment.equals("development")) {
    		
    		String appPath = System.getProperty("user.dir");
    		
    		scriptPath = appPath + "/" + jenkinsDevDestroyScript;
    		
        } else {
        	
        	scriptPath = jenkinsDevDestroyScript;
        }

        TerraformResult terraformResult = null;

        BufferedReader stdInput = runProcess(scriptPath);

        String input = null;
        
        StringBuilder error = new StringBuilder();

        try {

            while ((input = stdInput.readLine()) != null) {

                logger.debug(input);

                if (input.startsWith("Destroy")) {

                    terraformResult = captureProcessingResults(input);
                    
                } else if (input.startsWith("Error")) {
                	
                	error.append(input);
                	
                	logger.error(error.toString());
                }
                
            }
            
        } catch(IOException ioe) {
        	
        	logger.error(error.toString());

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

    /**
     * This script captures the output of the Terraform logs
     * @param logCapture
     * @return TerraformResult
     */
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

        } else if (logCapture.startsWith("private_ip_address")) {

            String[] result = logCapture.split("=");

            terraformResult.setHost(result[1]);

        } else if (logCapture.startsWith("Destroy")) {

            terraformResult = new TerraformDestroyResult();
            String[] result = logCapture.split(" ");

            terraformResult.setAction(result[0]);
            terraformResult.setStatus(result[1]);
            terraformResult.setResourcesDestroyed(Integer.parseInt(result[3]));
        }

        logger.debug("Capture result: " + terraformResult);

        return terraformResult;
     }
}
