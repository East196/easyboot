package com.github.east196.rap.sms;

// TODO: Spring Boot 3 需要迁移到 Spring Cloud 2023.x 或改用 RestTemplate/WebClient
// 暂时注释掉 FeignClient

/*
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
*/

public interface SmsClient {
	// 占位，待实现
}
