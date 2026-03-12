package com.github.east196.rap.sms;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: FeignClient 需要重构
 */
@RestController
public class SmsController {

	@RequestMapping(value = "/api/v1/vcode", method = RequestMethod.POST)
	public VcodeResp vcode(@RequestBody VcodeReq vcodeReq) {
		// TODO: 需要重构 SmsClient
		return new VcodeResp();
	}
	
	@RequestMapping(value = "/api/v1/event", method = RequestMethod.POST)
	public EventResp event(@RequestBody EventReq eventReq) {
		// TODO: 需要重构 SmsClient
		return new EventResp();
	}
	
}
