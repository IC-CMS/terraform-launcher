package cms.sre.terraform.controller;

import cms.sre.terraform.model.GitlabPushEvent;
import cms.sre.terraform.model.TerraformDestroyEvent;
import cms.sre.terraform.model.TerraformResult;
import cms.sre.terraform.service.TerraformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping (value = "/terraform")
public class TerraformController {

    private static final Logger logger = LoggerFactory.getLogger("TerraformController.class");

    @Autowired
    private TerraformService terraformService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "{status: 'ok'}";
    }

    @RequestMapping(value = "/launch", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String launch(@RequestBody String requestBody) throws IOException {

        try {

            JSONObject json = new JSONObject(requestBody);
            String prettyString = json.toString(4);

            logger.debug(prettyString);
        } catch (Exception e) {

            e.printStackTrace();
        }

        GitlabPushEvent event = mapper.readValue(requestBody, GitlabPushEvent.class);

        logger.debug("Event info: " + event);

        TerraformResult launchResult = terraformService.processRequest(event);

        String json = mapper.writeValueAsString(launchResult);

        return json;
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody String destroy(@RequestBody String requestBody) throws IOException {

        try {

            JSONObject json = new JSONObject(requestBody);
            String prettyString = json.toString(4);

            logger.debug(prettyString);

        } catch (Exception e) {

            e.printStackTrace();
        }

        TerraformDestroyEvent event = mapper.readValue(requestBody, TerraformDestroyEvent.class);

        logger.debug("Event info: " + event);

        TerraformResult destroyResult = terraformService.processRequest(event);

        String json = mapper.writeValueAsString(destroyResult);

        return json;
    }

}
