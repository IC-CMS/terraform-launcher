package cms.sre.terraform.health;

import cms.sre.terraform.config.AppConfig;
import cms.sre.terraform.controller.JenkinsJobController;
import cms.sre.terraform.controller.TerraformController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ComponentScan("cms.sre")
public class HealthCheck implements HealthIndicator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    AppConfig appConfig;

    @Autowired
    TerraformController terraformController;

    @Autowired
    JenkinsJobController jenkinsJobController;

    @Autowired
    public HealthCheck(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public Health health() {

        int errorCode = checkTerraformController(); // perform some specific health check

        if (errorCode != 0) {
            return Health.down()
                    .withDetail("TerraformController Error", errorCode).build();
        }

        errorCode = checkJenkinsJobController(); // perform some specific health check

        if (errorCode != 0) {
            return Health.down()
                    .withDetail("JenkinsJobController Error", errorCode).build();
        }

        errorCode = this.checkApplicationConfig();

        if (errorCode != 0) {
            return Health.down()
                    .withDetail("ApplicationConfig Error", errorCode).build();
        }
        return Health.up().build();
    }


    private int checkTerraformController() {

        if (terraformController == null) {
            return 1;
        }

        return 0;
    }

    private int checkJenkinsJobController() {

        if (jenkinsJobController == null) {
            return 1;
        }

        return 0;
    }

    private int checkApplicationConfig() {

        if (((appConfig.getJenkinsJobRunnerScript() == null || appConfig.getJenkinsJobRunnerScript().equals("")) ||
                (appConfig.getJenkinsLaunchScript() == null || appConfig.getJenkinsLaunchScript().equals("")) ||
                (appConfig.getJenkinsDestroyScript() == null || appConfig.getJenkinsDestroyScript().equals("")) ||
                (appConfig.getEnvironment() == null || appConfig.getEnvironment().equals("")))) {

            return 1;

        } else {

            return 0;
        }
    }
}