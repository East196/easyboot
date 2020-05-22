package com.github.east196.rap.sms;

import lombok.Data;

@Data
/**
 * {"name":"熊猫大人","address":"中科院","device":"矿泉水瓶","time":"12:12","event":"喝完事件"}
 * 
 * @author east196
 *
 */
public class EventReq {
	String phone;
	String name;
	String address;
	String device;
	String time;
	String event;
}
