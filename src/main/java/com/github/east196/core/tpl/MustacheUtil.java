package com.github.east196.core.tpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.SneakyThrows;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;

public class MustacheUtil {

    @SneakyThrows
    public static String parseExpression(String template, ObjectNode objectNode) {
        HashMap<String, Object> scopes = toMap(objectNode);
        return parseExpression(template, scopes);
    }

    @SneakyThrows
    public static String parseExpression(String template, HashMap<String, Object> scopes) {
        Writer writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(template), "example");
        mustache.execute(writer, scopes);
        return writer.toString();
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
