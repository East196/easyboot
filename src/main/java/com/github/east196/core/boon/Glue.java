package com.github.east196.core.boon;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.lang.Validator;

import com.github.east196.core.api.Result;
import com.google.common.io.Files;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;

import cn.hutool.core.util.StrUtil;
import com.google.gson.GsonBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * 模拟各种各样匪夷所思的工具类
 * 集中解决common-lang common-io hutool fastjson joda-time等常用工具类的归一化
 * @author east196
 *
 */
public class Glue {

    public static boolean isNotBlank(String paramKey) {
        return StrUtil.isNotBlank(paramKey);
    }

    public static String mapToString(Map<String, String[]> paramMap){
        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {

            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            String obj = StrUtil.endWithIgnoreCase(param.getKey(), "password") ? "你是看不见我的" : paramValue;
            params.put(key,obj);
        }
        return new Gson().toJson(params);
    }

    public static String mapToStringAll(Map<String, String[]> paramMap){
        if (paramMap == null) {
            return "";
        }
        Map<String, Object> params = new HashMap<>(16);
        for (Map.Entry<String, String[]> param : paramMap.entrySet()) {
            String key = param.getKey();
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.put(key, paramValue);
        }
        return new Gson().toJson(params);
    }
    /**
     * https://www.cnblogs.com/baiqiantao/p/7512336.html
     */
    public static final Gson GSON = new GsonBuilder().setLenient().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    /**
     * https://www.jianshu.com/p/d62c2be60617
     */
    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;

        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }

        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }

        @Override
        public Type getRawType() {
            return raw;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    public static <T> Result<T> fromResultWithJsonObject(String reader, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(Result.class, new Class[]{clazz});
        return GSON.fromJson(reader, type);
    }

    public static <T> Result<List<T>> fromResultWithJsonArray(String reader, Class<T> clazz) {
        // 生成List<T> 中的 List<T>
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        // 根据List<T>生成完整的Result<List<T>>
        Type type = new ParameterizedTypeImpl(Result.class, new Type[]{listType});
        return GSON.fromJson(reader, type);
    }

    public static <T> T fromJsonObject(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    public static <T> List<T> fromJsonArray(String jsonArray, Class<T> clazz) {
        Type type = new ParameterizedTypeImpl(List.class, new Class[]{clazz});
        return GSON.fromJson(jsonArray, type);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }


    public static String getFileExtension(String filename) {
        if (filename.endsWith(".tar.gz")) {
            return "tar.gz";
        }
        return Files.getFileExtension(filename);
    }

    public static String getNameWithoutExtension(String filename) {
        String nameWithoutExtension = Files.getNameWithoutExtension(filename);
        if (filename.endsWith(".tar.gz")) {
            nameWithoutExtension += ".tar";
        }
        return nameWithoutExtension;
    }

    public static void assertNotNull(String message, Object object) {
        Validator.validateNotEmpty(object, message);
    }


    public static boolean notEmpty(Object obj) {
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object obj) {
        return length(obj) == 0 || "null".equals(obj);
    }

    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).size();
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        } else {
            return 1;
        }
    }

    public static final Pattern COMPILE = Pattern.compile("\\s*|\t|\r|\n");


    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }

    public static String decode(String strIn, String sourceCode, String targetCode) {
        String temp = code2code(strIn, sourceCode, targetCode);
        return temp;
    }

    public static String StrToUTF(String strIn, String sourceCode, String targetCode) {
        strIn = "";
        try {
            strIn = new String(strIn.getBytes("ISO-8859-1"), "GBK");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strIn;

    }

    private static String code2code(String strIn, String sourceCode, String targetCode) {
        String strOut = null;
        if (strIn == null || "".equals(strIn.trim())) {
            return strIn;
        }
        try {
            byte[] b = strIn.getBytes(sourceCode);
            for (int i = 0; i < b.length; i++) {
                System.out.print(b[i] + "  ");
            }
            strOut = new String(b, targetCode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return strOut;
    }

    public static int getInt(String s, int defval) {
        if (s == null || s == "") {
            return (defval);
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static int getInt(String s) {
        if (s == null || s == "") {
            return 0;
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static int getInt(String s, Integer df) {
        if (s == null || s == "") {
            return df;
        }
        try {
            return (Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Integer[] getInts(String[] s) {
        Integer[] integer = new Integer[s.length];
        if (s == null) {
            return null;
        }
        for (int i = 0; i < s.length; i++) {
            integer[i] = Integer.parseInt(s[i]);
        }
        return integer;

    }

    public static double getDouble(String s, double defval) {
        if (s == null || s == "") {
            return (defval);
        }
        try {
            return (Double.parseDouble(s));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static double getDou(Double s, double defval) {
        if (s == null) {
            return (defval);
        }
        return s;
    }

	/*public static Short getShort(String s) {
		if (StringUtil.isNotEmpty(s)) {
			return (Short.parseShort(s));
		} else {
			return null;
		}
	}*/

    public static int getInt(Object object, int defval) {
        if (isEmpty(object)) {
            return (defval);
        }
        try {
            return (Integer.parseInt(object.toString()));
        } catch (NumberFormatException e) {
            return (defval);
        }
    }

    public static Integer getInt(Object object) {
        if (isEmpty(object)) {
            return null;
        }
        try {
            return (Integer.parseInt(object.toString()));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static int getInt(BigDecimal s, int defval) {
        if (s == null) {
            return (defval);
        }
        return s.intValue();
    }

    public static Integer[] getIntegerArry(String[] object) {
        int len = object.length;
        Integer[] result = new Integer[len];
        try {
            for (int i = 0; i < len; i++) {
                result[i] = new Integer(object[i].trim());
            }
            return result;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String getString(String s) {
        return (getString(s, ""));
    }

    /**
     * 转义成Unicode编码
     * @param //s
     * @return
     */
	/*public static String escapeJava(Object s) {
		return StringEscapeUtils.escapeJava(getString(s));
	}*/

    public static String getString(Object object) {
        if (isEmpty(object)) {
            return "";
        }
        return (object.toString().trim());
    }

    public static String getString(int i) {
        return (String.valueOf(i));
    }

    public static String getString(float i) {
        return (String.valueOf(i));
    }

    public static String getString(String s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.trim());
    }

    public static String getString(Object s, String defval) {
        if (isEmpty(s)) {
            return (defval);
        }
        return (s.toString().trim());
    }

    public static long stringToLong(String str) {
        Long test = new Long(0);
        try {
            test = Long.valueOf(str);
        } catch (Exception e) {
        }
        return test.longValue();
    }

    /**
     * 获取本机IP
     */
    public static String getIp() {
        String ip = null;
        try {
            InetAddress address = InetAddress.getLocalHost();
            ip = address.getHostAddress();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * 判断一个类是否为基本数据类型。
     *
     * @param clazz
     *            要判断的类。
     * @return true 表示为基本数据类型。
     */
    private static boolean isBaseDataType(Class clazz) throws Exception {
        return (clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz.isPrimitive());
    }

    /**
     * @param request
     *            IP
     * @return IP Address
     */
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @return 本机IP
     * @throws SocketException
     */
    public static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    /**
     * java去除字符串中的空格、回车、换行符、制表符
     *
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = COMPILE;
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;

    }

    /**
     * 判断元素是否在数组内
     *
     * @param substring
     * @param source
     * @return
     */
    public static boolean isIn(String substring, String[] source) {
        if (source == null || source.length == 0) {
            return false;
        }
        for (int i = 0; i < source.length; i++) {
            String aSource = source[i];
            if (aSource.equals(substring)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Map对象
     */
    public static Map<Object, Object> getHashMap() {
        return new HashMap<Object, Object>();
    }

    /**
     * SET转换MAP
     *
     * @param setobj
     * @return
     */
    public static Map<Object, Object> SetToMap(Set<Object> setobj) {
        Map<Object, Object> map = getHashMap();
        for (Iterator iterator = setobj.iterator(); iterator.hasNext();) {
            Map.Entry<Object, Object> entry = (Map.Entry<Object, Object>) iterator.next();
            map.put(entry.getKey().toString(), entry.getValue() == null ? "" : entry.getValue().toString().trim());
        }
        return map;

    }

    public static boolean isInnerIP(String ipAddress) {
        boolean isInnerIp = false;
        long ipNum = getIpNum(ipAddress);
        /**
         * 私有IP：A类 10.0.0.0-10.255.255.255 B类 172.16.0.0-172.31.255.255 C类 192.168.0.0-192.168.255.255 当然，还有127这个网段是环回地址
         **/
        long aBegin = getIpNum("10.0.0.0");
        long aEnd = getIpNum("10.255.255.255");
        long bBegin = getIpNum("172.16.0.0");
        long bEnd = getIpNum("172.31.255.255");
        long cBegin = getIpNum("192.168.0.0");
        long cEnd = getIpNum("192.168.255.255");
        isInnerIp = isInner(ipNum, aBegin, aEnd) || isInner(ipNum, bBegin, bEnd) || isInner(ipNum, cBegin, cEnd) || "127.0.0.1".equals(ipAddress);
        return isInnerIp;
    }

    private static long getIpNum(String ipAddress) {
        String[] ip = ipAddress.split("\\.");
        long a = Integer.parseInt(ip[0]);
        long b = Integer.parseInt(ip[1]);
        long c = Integer.parseInt(ip[2]);
        long d = Integer.parseInt(ip[3]);

        long ipNum = a * 256 * 256 * 256 + b * 256 * 256 + c * 256 + d;
        return ipNum;
    }

    private static boolean isInner(long userIp, long begin, long end) {
        return (userIp >= begin) && (userIp <= end);
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。
     * 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->helloWorld
     *
     * @param name
     *            转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            //update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
            //update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
            return name.substring(0, 1).toLowerCase() + name.substring(1).toLowerCase();
            //update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。
     * 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world,test_id->helloWorld,testId
     *
     * @param names
     *            转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelNames(String names) {
        if(names==null|| "".equals(names)){
            return null;
        }
        StringBuffer sf = new StringBuffer();
        String[] fs = names.split(",");
        for (String field : fs) {
            field = camelName(field);
            sf.append(field + ",");
        }
        String result = sf.toString();
        return result.substring(0, result.length() - 1);
    }

    //update-begin--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能
    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。(首字母写)
     * 如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：hello_world->HelloWorld
     *
     * @param name
     *            转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelNameCapFirst(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 其他的驼峰片段，首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }
    //update-end--Author:zhoujf  Date:20180503 for：TASK #2500 【代码生成器】代码生成器开发一通用模板生成功能

    /**
     * 将驼峰命名转化成下划线
     * @param para
     * @return
     */
    public static String camelToUnderline(String para){
        if(para.length()<3){
            return para.toLowerCase();
        }
        StringBuilder sb=new StringBuilder(para);
        int temp=0;//定位
        //从第三个字符开始 避免命名不规范
        for(int i=2;i<para.length();i++){
            if(Character.isUpperCase(para.charAt(i))){
                sb.insert(i+temp, "_");
                temp+=1;
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 随机数
     * @param place 定义随机数的位数
     */
    public static String randomGen(int place) {
        String base = "qwertyuioplkjhgfdsazxcvbnmQAZWSXEDCRFVTGBYHNUJMIKLOP0123456789";
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }

    /**
     * 获取类的所有属性，包括父类
     *
     * @param object
     * @return
     */
    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    /**
     * 将map的key全部转成小写
     * @param list
     * @return
     */
    public static List<Map<String, Object>> toLowerCasePageList(List<Map<String, Object>> list){
        List<Map<String, Object>> select = new ArrayList<>();
        for (Map<String, Object> row : list) {
            Map<String, Object> resultMap = new HashMap<>();
            Set<String> keySet = row.keySet();
            for (String key : keySet) {
                String newKey = key.toLowerCase();
                resultMap.put(newKey, row.get(key));
            }
            select.add(resultMap);
        }
        return select;
    }
}
