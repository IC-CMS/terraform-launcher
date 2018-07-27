package cms.sre.terraform.controller;

import cms.sre.terraform.model.WebHookMicroServiceEvent;
import cms.sre.terraform.model.TerraformDestroyEvent;
import cms.sre.terraform.model.TerraformResult;
import cms.sre.terraform.service.TerraformService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This controller is for receiving requests to execute various shell scripts to launch and destroy terraform instances.
 */
@RestController
@RequestMapping(value = "/terraform")
public class TerraformController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TerraformService terraformService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "{status: 'ok'}";
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String launch(@RequestBody String requestBody) throws IOException {

        try {

            JSONObject json = new JSONObject(requestBody);
            String prettyString = json.toString(4);

            logger.debug(prettyString);

        } catch (Exception e) {

            e.printStackTrace();
        }

        WebHookMicroServiceEvent event = mapper.readValue(requestBody, WebHookMicroServiceEvent.class);

        logger.debug("Event info: " + event);

        if (validateApplyRequest(event)) {

            TerraformResult launchResult = terraformService.processRequest(event);

            String json = mapper.writeValueAsString(launchResult);

            return json;

        } else {

            throw new IllegalArgumentException();
        }
    }

    @RequestMapping(value = "/destroy", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    String destroy(@RequestBody String requestBody) throws IOException {

        try {

            JSONObject json = new JSONObject(requestBody);
            String prettyString = json.toString(4);

            logger.debug(prettyString);

        } catch (Exception e) {

            e.printStackTrace();
        }

        TerraformDestroyEvent event = mapper.readValue(requestBody, TerraformDestroyEvent.class);

        logger.debug("Event info: " + event);

        if (validateDestroyRequest(event)) {

            TerraformResult destroyResult = terraformService.processRequest(event);

            String json = mapper.writeValueAsString(destroyResult);

            return json;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    void handleBadRequests(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Missing required value from WebHook Microservice");
    }

    /**
     * Validate that the minimum required fields required for WebHook event
     * @param event
     * @return
     */
     private boolean validateApplyRequest(WebHookMicroServiceEvent event) {

        if ((event.getProject_name() != null) &&
                (event.getTimestamp() != null) &&
                (event.getUser_name() != null) &&
                (event.getSsl_url() != null) &&
                (event.getObject_kind() != null) &&
                (event.getBranch_name() !=null) &&
                (event.getClassification() != null)) {

            return true;

        }

        return false;

    }

    /**
     * Validate that the minimum required fields are present for Destroy event
     * @param event
     * @return
     */
    private boolean validateDestroyRequest(TerraformDestroyEvent event) {

        if ((event.getType() != null) &&
                (event.getProject() != null) &&
                (event.getServer() != null)) {

            return true;

        }

        return false;

    }
}