package com.github.east196.ezsb.mvc;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

@Slf4j
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params;
    /**
     * 存储body数据的容器
     */
    private final byte[] body;

    public ParameterRequestWrapper(HttpServletRequest request,
                                   Map<String, String[]> newParams) {
        super(request);
        this.params = newParams;

        // RequestDispatcher.forward parameter
        renewParameterMap(request);
        String bodyStr = getBodyString(request);
        body = bodyStr.getBytes(Charset.defaultCharset());
    }


    /**
     * 获取请求Body
     *
     * @param request request
     * @return String
     */
    public String getBodyString(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求Body
     *
     * @return String
     */
    public String getBodyString() {
        final InputStream inputStream = new ByteArrayInputStream(body);

        return inputStream2String(inputStream);
    }

    /**
     * 将inputStream里的数据读取出来并转换成字符串
     *
     * @param inputStream inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }

        return sb.toString();
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

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
        };
    }

    @Override
    public String getParameter(String name) {
        String result = "";

        Object v = params.get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            String[] strArr = (String[]) v;
            if (strArr.length > 0) {
                result = strArr[0];
            } else {
                result = null;
            }
        } else if (v instanceof String) {
            result = (String) v;
        } else {
            result = v.toString();
        }

        return result;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Vector<String>(params.keySet()).elements();
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] result = null;

        Object v = params.get(name);
        if (v == null) {
            result = null;
        } else if (v instanceof String[]) {
            result = (String[]) v;
        } else if (v instanceof String) {
            result = new String[] { (String) v };
        } else {
            result = new String[] { v.toString() };
        }

        return result;
    }

    private void renewParameterMap(HttpServletRequest req) {

        String queryString = req.getQueryString();

        if (queryString != null && queryString.trim().length() > 0) {
            String[] params = queryString.split("&");

            for (int i = 0; i < params.length; i++) {
                int splitIndex = params[i].indexOf("=");
                if (splitIndex == -1) {
                    continue;
                }

                String key = params[i].substring(0, splitIndex);

                if (!this.params.containsKey(key)) {
                    if (splitIndex < params[i].length()) {
                        String value = params[i].substring(splitIndex + 1);
                        this.params.put(key, new String[] { value });
                    }
                }
            }
        }
    }

}