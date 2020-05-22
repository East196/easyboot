package com.github.east196.core.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

@Slf4j
public class MapUtil {
    private static String key = "44d60a7dc6022d6d06a0a9bd9358b053";


    public static void main(String[] args) {
       // String latAndLon = getLatAndLogByName("深圳先进技术研究院");
       // log.info("latAndLon:{}",latAndLon);
        String nameByLatAndLog = getCodeByLatAndLog(22.5959151449, 113.9910882711);
        log.info("nameByLatAndLog:{}",nameByLatAndLog);

    }
    /**
     * 高德api 根据地址获取经纬度
     *
     * @param name
     * @return
     */
    @SneakyThrows
    public static String getLatAndLogByName(String name) {
        StringBuffer s = new StringBuffer();
        s.append("key=" + key + "&address=" + name);
        String res = sendPost("http://restapi.amap.com/v3/geocode/geo", s.toString());
        log.info("res:{}",res);
        JsonNode jsonObject = new ObjectMapper().readTree(res);
        log.info(jsonObject.path("geocodes").path(0).asText());
        String add = jsonObject.path("geocodes").path(0).path("location").asText();
        return add;
    }


    /**
     * 高德api 根据经纬度获取三级联动地址
     *
     * @param lat
     * @param lon
     * @return
     */
    @SneakyThrows
    public static String getCodeByLatAndLog(Double lat,Double lon) {
        StringBuffer s = new StringBuffer();
        s.append("key=" + key + "&location=" + lon+","+lat);
        String res = sendGet("https://restapi.amap.com/v3/geocode/regeo", s.toString());
        log.info("res:{}",res);
        JsonNode jsonObject = new ObjectMapper().readTree(res);
        log.info("jsonObject:{}",jsonObject);
        JsonNode regeocode = jsonObject.get("regeocode");
        log.info("regeocode:{}",regeocode);
        JsonNode addressComponent = regeocode.get("addressComponent");
        String adcode = addressComponent.get("adcode").asText();
        return adcode;
    }


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                log.info(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送GET请求出现异常！" + e);
            log.error("发送GET请求出现异常"+"_" + e.getMessage(),e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            log.info("发送 POST 请求出现异常！" + e);
            log.error("发送 POST 请求出现异常！" + e.getMessage(),e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * GET请求数据
     *
     * @param get_url url地址
     * @param content key=value形式
     * @return 返回结果
     * @throws Exception
     */
    public static String sendGetData(String get_url, String content) throws Exception {
        String result = "";
        URL getUrl = null;
        BufferedReader reader = null;
        String lines = "";
        HttpURLConnection connection = null;
        try {
            if (content != null && !"".equals(content)) {
                get_url = get_url + "?" + content;
            }
            // get_url = get_url + "?" + URLEncoder.encode(content, "utf-8");
            getUrl = new URL(get_url);
            connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();
            // 取得输入流，并使用Reader读取
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码
            while ((lines = reader.readLine()) != null) {
                result = result + lines;
            }
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (reader != null) {
                reader.close();
                reader = null;
            }
            connection.disconnect();
        }
    }

    /**
     * @param POST_URL url地址
     * @param content  key=value形式
     * @return 返回结果
     * @throws Exception
     */
    public static String sendPostData(String POST_URL, String content) throws Exception {
        HttpURLConnection connection = null;
        DataOutputStream out = null;
        BufferedReader reader = null;
        String line = "";
        String result = "";
        try {
            URL postUrl = new URL(POST_URL);
            connection = (HttpURLConnection) postUrl.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            // Post 请求不能使用缓存
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.connect();

            out = new DataOutputStream(connection.getOutputStream());
            // content = URLEncoder.encode(content, "utf-8");
            // DataOutputStream.writeBytes将字符串中的16位的unicode字符�?8位的字符形式写道流里�?
            out.writeBytes(content);
            out.flush();
            out.close();
            // 获取结果
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 设置编码
            while ((line = reader.readLine()) != null) {
                result = result + line;
            }
            return result;
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }
            if (reader != null) {
                reader.close();
                reader = null;
            }
            connection.disconnect();
        }
    }

    /*
     * 过滤掉html里不安全的标签，不允许用户输入这些标�?
     */
    public static String htmlFilter(String inputString) {
        // return inputString;
        String htmlStr = inputString; // 含html标签的字符串
        String textStr = "";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;

        try {
            String regEx_script = "<[\\s]*?(script|style)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(script|style)[\\s]*?>";
            String regEx_onevent = "on[^\\s]+=\\s*";
            String regEx_hrefjs = "href=javascript:";
            String regEx_iframe = "<[\\s]*?(iframe|frameset)[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?(iframe|frameset)" +
                    "[\\s]*?>";
            String regEx_link = "<[\\s]*?link[^>]*?/>";

            htmlStr = compile(regEx_script, CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = compile(regEx_onevent, CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = compile(regEx_hrefjs, CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = compile(regEx_iframe, CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
            htmlStr = compile(regEx_link, CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;
    }

}
