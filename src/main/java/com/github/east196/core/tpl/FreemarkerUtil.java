package com.github.east196.core.tpl;

import cn.hutool.core.lang.Pair;
import com.fasterxml.jackson.databind.node.ObjectNode;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;

public class FreemarkerUtil {

    @SneakyThrows
    public static String parseExpression(String template, ObjectNode objectNode) {
        HashMap<String, Object> dataModel = toMap(objectNode);
        return parseExpression(template, dataModel);
    }

    @SneakyThrows
    private static String parseExpression(String template, Object dataModel) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("template", template);
        configuration.setTemplateLoader(stringLoader);
        Template freemarkerTemplate = configuration.getTemplate("template", "utf-8");
        StringWriter writer = new StringWriter();
        freemarkerTemplate.process(dataModel, writer);
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
        String template = "${key}（网站注册验证码，请勿泄露），您尾号${value}的手机正在注册网站。";
        Pair<String, Integer> objectNode = new Pair<String, Integer>("usernamexxxx", 1);
        System.out.println(parseExpression(template, objectNode));
    }

}
