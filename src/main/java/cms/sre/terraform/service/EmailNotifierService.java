package cms.sre.terraform.service;

import cms.sre.dna_common_data_model.emailnotifier.SendEmailRequest;
import cms.sre.terraform.config.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * This service sends out notification emails on build results
 */
@Service
public class EmailNotifierService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    AppConfig appConfig;

    public boolean sendEmail(SendEmailRequest sendEmailRequest) {

        logger.debug("Email Request: " + sendEmailRequest);

        String jsonEmailRequest = null;

        try {
            jsonEmailRequest = mapper.writeValueAsString(sendEmailRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(jsonEmailRequest, headers);

        try {

            ResponseEntity<String> buildResponse = restTemplate.exchange(appConfig.getEmailNotifierUrl() + ":"
                            + appConfig.getEmailNotifierPort() + appConfig.getEmailNotifierPath(), HttpMethod.POST, entity, String.class);

            if (buildResponse.getStatusCode() == HttpStatus.OK) {

                logger.info("Successfully processed email request");

            } else if (buildResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {

                logger.error("Unable to process email request");

            }

        } catch (Exception e) {

            logger.error("Email Notifier Connection Error: " + e.getMessage());

        }

        return true;

    }

}