/*
 * @author east196
 */

package com.github.east196.core.poi;

import cn.afterturn.easypoi.word.entity.MyXWPFDocument;
import cn.afterturn.easypoi.word.parse.ParseWord07;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.github.east196.core.boon.Boon;

import java.io.InputStream;
import java.util.Map;

public class MyParseWord07 extends ParseWord07 {

    /**
     * 解析07版的Word并且进行赋值
     *
     * @return
     * @throws Exception
     * @author east196
     * 2019-12-23
     */
    public XWPFDocument parseWordDirect(String url, Map<String, Object> map) throws Exception {
        InputStream inputStream= Boon.getInputStream(url);
        MyXWPFDocument doc = new MyXWPFDocument(inputStream);
        parseWord(doc, map);
        return doc;
    }
}
