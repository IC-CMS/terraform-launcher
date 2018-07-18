package cms.sre.terraform.manager;

import cms.sre.terraform.TestConfiguration;
import cms.sre.terraform.model.JenkinsJobResult;
import cms.sre.terraform.model.TerraformResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@ContextConfiguration(classes = {TerraformRunner.class})
public class TerraformRunnerTest {

    @Autowired
    private TerraformRunner terraformRunner;

    @Test
    public void apply() {

        TerraformResult result = terraformRunner.apply();

    }

    @Test
    public void destroy() {

        TerraformResult result = terraformRunner.destroy();

    }


}