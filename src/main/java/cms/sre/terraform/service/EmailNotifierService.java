package cms.sre.terraform.service;

import cms.sre.dna_common_data_model.emailnotifier.SendEmailRequest;
import org.springframework.stereotype.Service;

/**
 * This service sends out notification emails on build results
 */
@Service
public class EmailNotifierService {

    SendEmailRequest sendEmailRequest;

    public String sendEmail(SendEmailRequest sendEmailRequest) {

        return null;

    }

}
