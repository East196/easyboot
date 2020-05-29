package com.alibaba.fastjson;

import java.util.Map.Entry;
import java.util.function.Predicate;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONObject {
	
	private JsonObject jsonObject;
	
    public JSONObject(JsonObject jsonObject){
    	this.jsonObject = jsonObject;
    }

	public JsonObject getJsonObject() {
		return jsonObject;
	}
	
    public int size() {
        return jsonObject.size();
    }

    public boolean isEmpty() {
        return jsonObject.size()==0;
    }

    public boolean containsKey(String key) {
        return jsonObject.has(key);
    }

    public boolean containsValue(Object value) {
        return jsonObject.entrySet().stream().anyMatch(new Predicate<Entry<String,JsonElement>>() {

			@Override
			public boolean test(Entry<String, JsonElement> entry) {
				return entry.getValue().equals(value);
			}
		});
    }

}
