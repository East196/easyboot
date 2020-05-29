package com.alibaba.fastjson;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class JSON {

	public static JSONObject parseObject(String text) {
		JsonObject jsonObject = new Gson().fromJson(text, JsonObject.class);
		return new JSONObject(jsonObject);
	}
}
