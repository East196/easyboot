package com.github.east196.ezsb.mvc;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BodyRequestWrapper extends HttpServletRequestWrapper {

    private byte[] requestBody = null;

    public BodyRequestWrapper(HttpServletRequest request, String body) {

        super(request);

        //将新的body赋值进去
        try {
            //requestBody = StreamUtils.copyToByteArray(request.getInputStream());
            if(body == null){
                body = "";
            }
            requestBody = body.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写 getInputStream()
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if(requestBody == null){
            requestBody= new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    /**
     * 重写 getReader()
     */
    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }



}