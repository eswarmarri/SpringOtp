package com.sample.otp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.sample.otp.service.OtpService;


@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {
	
	private final Logger logger = LoggerFactory.getLogger(OtpController.class);
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private AmazonSimpleEmailService amazonSimpleEmailService;

    @Value("${aws.ses.senderMailId}")
    private String senderMailId;

	@GetMapping("/generateOtp")
	private ResponseEntity<String> generateOtp(@RequestHeader String email){
		logger.info("Start generateOtp()");
		String response = null;
		String otp = null;
		Body bodyObj = null;
		Destination destination = null;
		Content subjectContent = null;
		Content bodyContent = null;
		Message message = null;
		SendEmailRequest request = null;
		SendEmailResult result = null;
		try {
			otp = otpService.generateOtp(email);
			//logger.info("Otp is {}",otp);
            bodyObj = new Body();
            destination = new Destination().withToAddresses(email);
            subjectContent = new Content().withData("OTP for Reset Password");
            bodyContent = new Content().withData("Your otp is - "+otp);
            bodyObj.withHtml(bodyContent);
            message = new Message().withBody(bodyObj).withSubject(subjectContent);
            request = new SendEmailRequest().withSource(senderMailId)
                    .withDestination(destination)
                    .withMessage(message);
            result = amazonSimpleEmailService.sendEmail(request);
		}
		catch(Exception e) {
			logger.error("Exception occured while generating otp",e);
			return new ResponseEntity<>("Exception occured while generating otp : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("End generateOtp");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@GetMapping("/verifyOtp")
	private ResponseEntity<String> verifyOtp(@RequestHeader String email, @RequestHeader String otp){
		logger.info("Start verifyOtp()");
		String response = null;
		try {
			if(otp.equalsIgnoreCase(otpService.generateOtp(email))) {
				response = "Otp verified successfully";
			}
			else {
				return new ResponseEntity<String>("Invalid otp",HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			logger.error("Exception occured while generating otp",e);
			return new ResponseEntity<>("Exception occured while generating otp : "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("End verifyOtp()");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
