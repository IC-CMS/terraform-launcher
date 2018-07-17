package cms.sre.terraform.controller;

import cms.sre.terraform.model.*;
import cms.sre.terraform.service.JenkinsJobService;
import cms.sre.terraform.service.TerraformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping (value = "/jenkinsjob")
public class JenkinsJobController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JenkinsJobService jenkinsJobService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "{status: 'ok'}";
    }

    @RequestMapping(value = "/run", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String run(@RequestBody String requestBody) throws IOException {

        try {

            JSONObject json = new JSONObject(requestBody);
            String prettyString = json.toString(4);

            logger.debug(prettyString);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JenkinsJobEvent event = mapper.readValue(requestBody, JenkinsJobEvent.class);

        logger.debug("Event info: " + event);

        JenkinsJobResult launchResult = jenkinsJobService.processRequest(event);

        String json = mapper.writeValueAsString(launchResult);

        return json;
    }

}