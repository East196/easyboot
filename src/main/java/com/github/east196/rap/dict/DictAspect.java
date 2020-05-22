package com.github.east196.rap.dict;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.east196.core.api.DataResponse;
import com.github.east196.core.api.Result;
import com.github.east196.core.api.TableResult;
import com.github.east196.core.boon.Boon;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 字典aop类
 * @Author: east196
 * @Date: 2019-3-17 21:50
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class DictAspect {
    private static final Logger logger = LoggerFactory.getLogger(DictAspect.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DictService dictService;

    // 定义切点Pointcut
   @Pointcut("execution(* com.github.east196.*..*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	long time1=System.currentTimeMillis();	
        Object result = pjp.proceed();
        long time2=System.currentTimeMillis();
        log.debug("获取JSON数据 耗时："+(time2-time1)+"ms");
        long start=System.currentTimeMillis();
        parseDictText(result);
        long end=System.currentTimeMillis();
        log.debug("解析注入JSON数据  耗时"+(end-start)+"ms");
        return result;
    }

    /**
     * 本方法针对返回对象为Result 的IPage的分页列表数据进行动态字典注入
     * 字典注入实现 通过对实体类添加注解@dict 来标识需要的字典内容,字典分为单字典code即可 ，table字典 code table text配合使用与原来jeecg的用法相同
     * 示例为SysUser   字段为sex 添加了注解@Dict(dicCode = "sex") 会在字典服务立马查出来对应的text 然后在请求list的时候将这个字典text，已字段名称加_dictText形式返回到前端
     * 例输入当前返回值的就会多出一个sex_dictText字段
     * {
     *      sex:1,
     *      sex_dictText:"男"
     * }
     * 前端直接取值sext_dictText在table里面无需再进行前端的字典转换了
     *  customRender:function (text) {
     *               if(text==1){
     *                 return "男";
     *               }else if(text==2){
     *                 return "女";
     *               }else{
     *                 return text;
     *               }
     *             }
     *             目前vue是这么进行字典渲染到table上的多了就很麻烦了 这个直接在服务端渲染完成前端可以直接用
     * @param data
     */
    private void parseDictText(Object data) throws IOException {
        if (data instanceof DataResponse) {
            Object datadata = ((DataResponse) data).getData();
            if (datadata==null || Boon.isPrimitiveOrString(datadata)){
                return;
            }
            log.info("datadata class? {},is primitive?  {}",datadata.getClass(),Boon.isPrimitiveOrString(datadata));
            if (((DataResponse) data).getData() instanceof IPage) {
                List<ObjectNode> items = new ArrayList<>();
                for (Object record : ((IPage) ((DataResponse) data).getData()).getRecords()) {
                    items.add(parseRecord(record));
                }
                ((IPage) ((DataResponse) data).getData()).setRecords(items);
            } else if (((DataResponse) data).getData() instanceof List) {
                List<ObjectNode> items = new ArrayList<>();
                for (Object record : ((List) ((DataResponse) data).getData())) {
                    items.add(parseRecord(record));
                }
                ((DataResponse<List>) data).setData(items);
            }else if(((DataResponse) data).getData() instanceof TableResult){
       /*         List<ObjectNode> items = new ArrayList<>();
                for (Object record : (List)((TableResult) ((DataResponse) data).getData()).getData()) {
                    items.add(parseRecord(record));
                }
                ((TableResult)((DataResponse) data).getData()).setData(items);*/
            }else {
                Object record= ((DataResponse) data).getData();
                //((DataResponse) data).setData(parseRecord(record));
                ((DataResponse) data).setData(parseRecord(record));
            }
        }else if(data instanceof Result){
            if (((Result) data).getResult() instanceof IPage) {
                List<ObjectNode> items = new ArrayList<>();
                for (Object record : ((IPage) ((Result) data).getResult()).getRecords()) {
                    items.add(parseRecord(record));
                }
                ((IPage) ((Result) data).getResult()).setRecords(items);
            }
        }
    }

    private String getJson(Object record) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String json = "{}";
        try {
            //解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
            json = mapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            logger.error("json解析失败" + e.getMessage());
            log.error(record+"_" + e.getMessage(),e);
            e.printStackTrace();
        }
        return json;
    }

    private String getDictText(ObjectNode item, Field field) {
        String code = field.getAnnotation(Dict.class).dicCode();
        String text = field.getAnnotation(Dict.class).dicText();
        String table = field.getAnnotation(Dict.class).dictTable();
        String key = item.get(field.getName()).asText();
        String textValue;
        if (!StringUtils.isEmpty(table)) {
            textValue = dictService.queryTableDictTextByKey(table, text, code, key);
            log.debug("table {}, {}->{}, {}->{},",table, code, key,text,textValue );
        } else {
            textValue = dictService.queryDictTextByKey(code, key);
            log.debug("dict {}, {}->{}",code, key,textValue );
        }
        return textValue;
    }

    private ObjectNode parseRecord(Object record){
        String json = getJson(record);
        ObjectNode item = null;
        try {
            item = objectMapper.readValue(json, ObjectNode.class);
        } catch (IOException e) {
            log.error(json+"_" + e.getMessage(),e);
            e.printStackTrace();
        }
        for (Field field : record.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Dict.class) != null) {
                String textValue = getDictText(item, field);
                item.put(field.getName() + "DictText", textValue);
            }else if(field.getAnnotation(HasDict.class)!=null){
                    parseRecord(item,field.getName(), BeanUtil.getProperty(record,field.getName()));
            }
        }
        return item;
    }

    private void parseRecord(ObjectNode up,String name,Object record){
        String json = getJson(record);
        ObjectNode item = null;
        try {
            item = objectMapper.readValue(json, ObjectNode.class);
        } catch (IOException e) {
            log.error(json+"_" + e.getMessage(),e);
            e.printStackTrace();
        }
        for (Field field : record.getClass().getDeclaredFields()) {
            if (field.getAnnotation(Dict.class) != null) {
                String textValue = getDictText(item, field);
                item.put(field.getName() + "DictText", textValue);
            }else if(field.getAnnotation(HasDict.class)!=null){
                parseRecord(item,field.getName(), BeanUtil.getProperty(record,field.getName()));
            }
        }
        up.set(name,item);
    }

}
