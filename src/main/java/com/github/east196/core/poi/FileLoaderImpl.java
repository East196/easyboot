/*
 * @author east196
 */

package com.github.east196.core.poi;

import cn.afterturn.easypoi.cache.manager.IFileLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 文件加载类,根据路径加载指定文件
 *
 * @author user
 * @date 2018/12/18
 */
@Slf4j
public class FileLoaderImpl implements IFileLoader {

    @Override
    public byte[] getFile(String url) {
        InputStream fileis = null;
        ByteArrayOutputStream baos = null;
        try {
            fileis = getInputStream(url);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int len;
            while ((len = fileis.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            byte[] result = baos.toByteArray();
            log.debug("result.length is {}", result.length);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(fileis);
            IOUtils.closeQuietly(baos);
        }
        log.error(fileis + "这个路径文件没有找到,请查询");
        return null;
    }

    @SneakyThrows
    private InputStream getInputStream(String url) {
        InputStream fileis = null;
        //判断是否是网络地址
        if (url.startsWith("http")) {
            URL urlObj = new URL(url);
            URLConnection urlConnection = urlObj.openConnection();
            urlConnection.setConnectTimeout(30);
            urlConnection.setReadTimeout(60);
            urlConnection.setDoInput(true);
            fileis = urlConnection.getInputStream();
        } else {
            //先用绝对路径查询,再查询相对路径
            try {
                fileis = new FileInputStream(url);
            } catch (FileNotFoundException e) {
                //获取项目文件
                fileis = FileLoaderImpl.class.getClassLoader().getResourceAsStream(url);
                if (fileis == null) {
                    fileis = FileLoaderImpl.class.getResourceAsStream(url);
                }
            }
        }
        log.debug("file inputstream is {}", fileis);
        return fileis;
    }
}