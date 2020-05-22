package com.github.east196.core.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;

import java.util.List;

public class JsonUtil {
    /**
     * List<T> 转 json 保存到数据库
     */
    public static <T> String listToJson(List<T> ts) {
        JSONArray jsons = JSONUtil.parseArray(ts);
        return jsons.toString();
    }

    /**
     * json 转 List<T>
     */
    public static <T> List<T> jsonToList(String jsonString, Class<T> clazz) {
        JSONArray jsonArray = JSONUtil.parseArray(jsonString);
        return JSONUtil.toList(jsonArray, clazz);
    }

}
