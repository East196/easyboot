package com.github.east196.core.tpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Iterator;

public class SpelUtil {

    @SneakyThrows
    public static String parseExpression(String template, ObjectNode objectNode) {
        // 0. 设置上下文
        EvaluationContext context = toSpelContext(objectNode);
        // 1. 构建解析器
        ExpressionParser parser = new SpelExpressionParser();

        // 2. 解析表达式
        Expression exp = parser.parseExpression(template, new TemplateParserContext());
        // 3. 获取结果
        return exp.getValue(context, String.class);
    }

    public static EvaluationContext toSpelContext(ObjectNode objectNode) {
        // 表达式的上下文
        EvaluationContext context = new StandardEvaluationContext();
        Iterator<String> fieldNames = objectNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            System.out.println(fieldName + " -> " + objectNode.get(fieldName).asText());
            context.setVariable(fieldName, objectNode.get(fieldName).asText());
        }
        return context;
    }


    public static void main(String[] args) {
        String template = "#{#vcode}（网站注册验证码，请勿泄露），您尾号#{#tail}的手机正在注册网站。";
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("vcode", "012345");
        objectNode.put("tail", "5678");
        System.out.println(parseExpression(template, objectNode));
    }

}
