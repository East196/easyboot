package com.github.east196.rap.dict;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DictSelectUtil {

    public  static String initDictOptions(String dictCode) throws IOException {
        return ajaxGetDictItems(dictCode);
    }

    // /sys/dict/getDictItems/${code}
    public static String ajaxGetDictItems(String dictCode) throws IOException {
        return Retrofit2Remote.getService(DictSelectApi.class).ajaxGetDictItems(dictCode).execute().body().toString();
    }
    // /sys/dict/getDictText/${dictCode}/${key}
    public static String ajaxFilterDictText(String dictCode,String key) throws IOException {
        return Retrofit2Remote.getService(DictSelectApi.class).ajaxFilterDictText(dictCode,key).execute().body().toString();
    }

    public  static String initDictOptions(List<String> dictCodes){
        return "";
    }
    public  static String filterDictText(List<SysDictItem> dictOptions, String dictItemValue){
        return dictOptions.stream().filter(sysDictItem -> sysDictItem.getItemValue().equals(dictItemValue)).findFirst().orElse(new SysDictItem()).getItemText();
    }


    public  static String filterMultiDictText(List<SysDictItem> dictOptions, String dictItemValues){
        List<String> itemValues = Arrays.<String>asList(dictItemValues.split(","));
        return dictOptions.stream().filter(sysDictItem -> itemValues.contains(sysDictItem.getItemValue())).map(sysDictItem -> sysDictItem.getItemText()).collect(Collectors.joining(""));
    }

    public static void main(String[] args) throws IOException {
        System.out.println(ajaxGetDictItems("sex"));
        System.out.println(ajaxFilterDictText("sex","1"));
    }

}
