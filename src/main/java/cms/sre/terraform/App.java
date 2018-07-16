package cms.sre.terraform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.groups.ConvertGroup;

@SpringBootApplication()
@ConfigurationProperties("App")
public class App {

    public static void main(String[] args) {

        SpringApplication.run(App.class, args);
    }


}
