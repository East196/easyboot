package com.github.east196.rap.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SmsController {

	@Autowired
	SmsClient smsClient;
	
	@RequestMapping(value = "/api/v1/vcode", method = RequestMethod.POST)
	public VcodeResp vcode(@RequestBody VcodeReq vcodeReq) {
		return smsClient.vcode(vcodeReq);
	}
	
	@RequestMapping(value = "/api/v1/event", method = RequestMethod.POST)
	public EventResp event(@RequestBody EventReq eventReq) {
		return smsClient.event(eventReq);
	}
	
}
