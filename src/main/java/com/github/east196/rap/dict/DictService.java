package com.github.east196.rap.dict;

public interface DictService {

    String queryDictTextByKey(String code, String key);

    String queryTableDictTextByKey(String table, String text, String code, String key);
}
