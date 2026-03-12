package com.github.east196.core.tpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

/**
 * TODO: Mustache 依赖需要更新
 */
public class MustacheUtil {

    public static String parseExpression(String template, ObjectNode objectNode) {
        HashMap<String, Object> scopes = toMap(objectNode);
        return parseExpression(template, scopes);
    }

    public static String parseExpression(String template, HashMap<String, Object> scopes) {
        // TODO: Mustache 依赖需要更新
        // 临时简单替换
        String result = template;
        for (String key : scopes.keySet()) {
            result = result.replace("{{" + key + "}}", String.valueOf(scopes.get(key)));
        }
        return result;
    }

    public static HashMap<String, Object> toMap(ObjectNode objectNode) {
        HashMap<String, Object> scopes = new HashMap<>();
        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            System.out.println(fieldName + " -> " + objectNode.get(fieldName).asText());
            scopes.put(fieldName, objectNode.get(fieldName).asText());
        }
        return scopes;
    }

    public static void main(String[] args) {
        ObjectNode scopes = new ObjectMapper().createObjectNode();
        scopes.put("name", "Mustache");
        scopes.put("feature", "Perfect!");

        String template = "{{name}}, {{feature}}!";

        System.out.println(parseExpression(template, scopes));
    }
}
