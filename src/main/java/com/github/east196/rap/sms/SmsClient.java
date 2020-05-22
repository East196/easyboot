package com.github.east196.rap.sms;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "${feign.sms.url}", name = "smsClient")
public interface SmsClient {
	@RequestMapping(value = "/sms/xyc/vcode", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public VcodeResp vcode(@RequestBody VcodeReq vcodeReq);
	
	@RequestMapping(value = "/sms/xyc/event", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public EventResp event(@RequestBody EventReq eventReq);
	
}
