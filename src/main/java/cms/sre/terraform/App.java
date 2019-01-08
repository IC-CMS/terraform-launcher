package cms.sre.terraform;

import cms.sre.terraform.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication()
public class App {

    public static void main(String[] args) {

        String property = System.getenv("TOASTERS_DIR");

        if (property == null) {

            System.out.println("A required System Property'TOASTERS_DIR' is not set");
            System.exit(-1);
        }

        SpringApplication.run(App.class, args);
    }
}