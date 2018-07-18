package cms.sre.terraform.manager;

import cms.sre.terraform.TestConfiguration;
import cms.sre.terraform.model.JenkinsJobResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestConfiguration.class)
@ContextConfiguration(classes = {JenkinsJobRunner.class})
public class JenkinsJobRunnerTest {

    @Autowired
    private JenkinsJobRunner jenkinsJobRunner;

    @Test
    public void runJob() {

        JenkinsJobResult result = jenkinsJobRunner.run("localhost", "git@github.com:IC-CMS/mock-project.git", "SomeProject");

    }


}