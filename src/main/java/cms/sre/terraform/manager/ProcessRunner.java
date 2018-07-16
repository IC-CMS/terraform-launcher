package cms.sre.terraform.manager;

import cms.sre.terraform.model.TerraformResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class ProcessRunner {

    public static final Logger logger = LoggerFactory.getLogger("ProcessRunner.class");

    ProcessBuilder processBuilder = new ProcessBuilder();

    Process process = null;

    protected BufferedReader runProcess(String script) {

        BufferedReader stdInput = null;

        BufferedReader errorInput = null;

        processBuilder.command("bash", "-c", script);

        try {

            process = processBuilder.start();

            try {

                process.waitFor();

            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            errorInput = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String input = null;

            String error = null;

            while ((error = errorInput.readLine()) != null) {
                logger.debug("Error: " + error);
            }

        } catch (IOException e) {

            //TODO handle the error
            e.printStackTrace();
        }

        return stdInput;

    }

    public void destroyProcess() {

        if (process != null) {

            process.destroy();
        }
    }

}
