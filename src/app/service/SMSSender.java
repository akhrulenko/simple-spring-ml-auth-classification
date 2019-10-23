package app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SMSSender {

	@Value("${twilio.sid}")
	private String sid;

	@Value("${twilio.token}")
	private String token;

	@Value("${twilio.phone}")
	private String phone;

	public SMSSender() {
		Twilio.init(sid, token);
	}

	public void sendSMS(String to, String message) {
		Message sms = Message.creator(new PhoneNumber(to), new PhoneNumber(phone), message).create();
	}
}