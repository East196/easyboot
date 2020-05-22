package com.github.east196.core.util;

import java.util.regex.Pattern;

/**
 * 验证工具
 *
 */
public class Validators {


    /**
     * 正则表达式：数字校验
     * **/
    public static final String REGEX_NUMBER="^[0-9]*$";

	/**
     * 正则表达式：验证密码
     */
    public static final String REGEX_ENGLISH_OR_NUMBER = "^[a-zA-Z0-9}]$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_ENGLISH_OR_NUMBER_LENGTH = "^[a-zA-Z0-9}]{6,12}$";

    /**
     * 正则表达式：验证用户名(英文)
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$";

    /**
     * 正则表达式：验证用户名(英文、数字)
     */
    public static final String REGEX_USERNAME_PLUS = "^[a-zA-Z0-9]{4,10}$";

    /**
     * 正则表达式：中文、英文、数字
     */
    public static final String REGEX_CHINESE_OR_ENGLISH_OR_NUMBER = "^[\u4e00-\u9fa5A-Za-z0-9]$";
    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
 
    /**
     * 正则表达式：验证汉字
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    /**
     * 正则表达式：验证汉字,长度在2-8之间
     */
    public static final String REGEX_CHINESE_LENGTH = "^[\u4e00-\u9fa5]{2,8}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证文件路径
     */
    public static final String REGEX_PATH="([a-zA-Z]/:)(//[^///:*?<>\"|]*)*(/.[a-zA-Z_0-9]{2,6})";

    /**
     * 正则表达式：验证URL
     */
    public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
 
    /**
     * 正则表达式：验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";



    /**
     * 正则表达式：验证phone
     * 验证规则: 11位数字长度，不能是（10，11，12）开头
     * 注解：@Pattern(regexp = Validators.REGEX_MOBILE ,message = Validators.MSG_MOBILE)
     */
    public static final String REGEX_MOBILE = "^(13|14|15|16|17|18|19)[0-9]{9}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_MOBILE: result.setCode(4501);break;
     */
    public static final String MSG_MOBILE = "请输入正确的手机号码";


    /**
     * 正则表达式：验证password
     * 验证规则: 6-20长度，只能是英文和数字
     * 注解：@Pattern(regexp = Validators.REGEX_PASSWORD ,message = Validators.MSG_PASSWORD)
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_PASSWORD: result.setCode(4502);break;
     */
    public static final String MSG_PASSWORD = "密码格式不对：请输入6-20个字符，只能是字母，数字";


    /**
     * 正则表达式：验证passwordP
     * 验证规则: 6-20长度，只能是英文和数字和下划线和标点符号
     * 注解：@Pattern(regexp = Validators.REGEX_PASSWORD_P ,message = Validators.MSG_PASSWORD_P)
     */
    public static final String REGEX_PASSWORD_P = "^([a-zA-Z0-9_]|[，。？：；‘’！“”—……、]|[-,.?:;'\"!`]){6,20}$待补充";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_PASSWORD_P: result.setCode(4503);break;
     */
    public static final String MSG_PASSWORD_P = "密码格式不对：请输入6-20个字符，只能是字母，数字，下划线，标点符号";


    /**
     * 正则表达式：验证chEnNum
     * 验证规则: 1-32长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_CH_EN_NUM ,message = Validators.MSG_CH_EN_NUM)
     */
    public static final String REGEX_CH_EN_NUM = "^[\u4e00-\u9fa5A-Za-z0-9]{2,64}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_CH_EN_NUM: result.setCode(4504);break;
     */
    public static final String MSG_CH_EN_NUM = "格式不对，请输入1-32个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证usernameC
     * 验证规则: 1-15长度，只能是汉字
     * 注解：@Pattern(regexp = Validators.REGEX_USERNAME_C ,message = Validators.MSG_USERNAME_C)
     */
    public static final String REGEX_USERNAME_C = "^[\u4e00-\u9fa5]{2,30}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_USERNAME_C: result.setCode(4505);break;
     */
    public static final String MSG_USERNAME_C = "名称格式不对，请输入1-15个字符，只能是汉字";


    /**
     * 正则表达式：验证usernameE
     * 验证规则: 1-30长度，只能是字母
     * 注解：@Pattern(regexp = Validators.REGEX_USERNAME_E ,message = Validators.MSG_USERNAME_E)
     */
    public static final String REGEX_USERNAME_E = "^[A-Za-z]{1,30}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_USERNAME_E: result.setCode(4506);break;
     */
    public static final String MSG_USERNAME_E = "名称格式不对，请输入1-30个字符，只能是字母";


    /**
     * 正则表达式：验证usernameCE
     * 验证规则: 1-15长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_USERNAME_C_E ,message = Validators.MSG_USERNAME_C_E)
     */
    public static final String REGEX_USERNAME_C_E = "^[\u4e00-\u9fa5A-Za-z0-9]{2,30}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_USERNAME_C_E: result.setCode(4507);break;
     */
    public static final String MSG_USERNAME_C_E = "名称格式不对，请输入1-15个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证firmName
     * 验证规则: 1-30长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_FIRM_NAME ,message = Validators.MSG_FIRM_NAME)
     */
    public static final String REGEX_FIRM_NAME = "^[\u4e00-\u9fa5A-Za-z0-9]{2,60}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_FIRM_NAME: result.setCode(4521);break;
     */
    public static final String MSG_FIRM_NAME = "厂商名称格式不对，请输入1-30个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证linkman
     * 验证规则: 1-15长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_LINKMAN ,message = Validators.MSG_LINKMAN)
     */
    public static final String REGEX_LINKMAN = "^[\u4e00-\u9fa5A-Za-z0-9]{2,30}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_LINKMAN: result.setCode(4522);break;
     */
    public static final String MSG_LINKMAN = "联系人名称格式不对，请输入1-15个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证position
     * 验证规则: 1-15长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_POSITION ,message = Validators.MSG_POSITION)
     */
    public static final String REGEX_POSITION = "^[\u4e00-\u9fa5A-Za-z0-9]{2,30}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_POSITION: result.setCode(4523);break;
     */
    public static final String MSG_POSITION = "职位名称格式不对，请输入1-15个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证projectName
     * 验证规则: 1-30长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_PROJECT_NAME ,message = Validators.MSG_REGEX_PROJECT_NAME)
     */
    public static final String REGEX_REGEX_PROJECT_NAME = "^[\u4e00-\u9fa5A-Za-z0-9]{2,60}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_PROJECT_NAME: result.setCode(4524);break;
     */
    public static final String MSG_REGEX_PROJECT_NAME = "项目名称格式不对，请输入1-30个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证address
     * 验证规则: 1-32长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_ADDRESS ,message = Validators.MSG_REGEX_ADDRESS)
     */
    public static final String REGEX_REGEX_ADDRESS = "^[\u4e00-\u9fa5A-Za-z0-9]{2,64}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_ADDRESS: result.setCode(4525);break;
     */
    public static final String MSG_REGEX_ADDRESS = "地址格式不对，请输入1-32个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证addressCode
     * 验证规则: 6位数字长度，只能是（1，2，3）开头
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_ADDRESS_CODE ,message = Validators.MSG_REGEX_ADDRESS_CODE)
     */
    public static final String REGEX_REGEX_ADDRESS_CODE = "^[1-3]{6}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_ADDRESS_CODE: result.setCode(4526);break;
     */
    public static final String MSG_REGEX_ADDRESS_CODE = "地址码格式不对";


    /**
     * 正则表达式：验证personName
     * 验证规则: 1-15长度，只能是汉字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_PERSON_NAME ,message = Validators.MSG_REGEX_PERSON_NAME)
     */
    public static final String REGEX_REGEX_PERSON_NAME = "^[\u4e00-\u9fa5]{2,30}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_PERSON_NAME: result.setCode(4527);break;
     */
    public static final String MSG_REGEX_PERSON_NAME = "姓名格式不对，请输入1-15个字符，只能是汉字";


    /**
     * 正则表达式：验证tracking
     * 验证规则: 1-18长度，只能是字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_TRACKING ,message = Validators.MSG_REGEX_TRACKING)
     */
    public static final String REGEX_REGEX_TRACKING = "^[A-Za-z0-9]{2,36}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_TRACKING: result.setCode(4528);break;
     */
    public static final String MSG_REGEX_TRACKING = "运单号格式不对";


    /**
     * 正则表达式：验证express
     * 验证规则: 1-32长度，只能是汉字，字母，数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_EXPRESS ,message = Validators.MSG_REGEX_EXPRESS)
     */
    public static final String REGEX_REGEX_EXPRESS = "^[\u4e00-\u9fa5A-Za-z0-9]{2,64}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_EXPRESS: result.setCode(4529);break;
     */
    public static final String MSG_REGEX_EXPRESS = "快递公司名称格式不对，请输入1-32个字符，只能是汉字，字母，数字";


    /**
     * 正则表达式：验证deviceNumber
     * 验证规则: 12-64长度，只能是英文和数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_DEVICE_NUMBER ,message = Validators.MSG_REGEX_DEVICE_NUMBER)
     */
    public static final String REGEX_REGEX_DEVICE_NUMBER = "^[a-zA-Z0-9]{12,64}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_DEVICE_NUMBER: result.setCode(4531);break;
     */
    public static final String MSG_REGEX_DEVICE_NUMBER = "设备编号格式不对";


    /**
     * 正则表达式：验证deviceIccid
     * 验证规则: 20长度，只能是数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_DEVICE_ICCID ,message = Validators.MSG_REGEX_DEVICE_ICCID)
     */
    public static final String REGEX_REGEX_DEVICE_ICCID = "^[0-9]{20}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_DEVICE_ICCID: result.setCode(4532);break;
     */
    public static final String MSG_REGEX_DEVICE_ICCID = "设备ICCID码格式不对";


    /**
     * 正则表达式：验证deviceImei
     * 验证规则: 15长度，只能是数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_DEVICE_IMEI ,message = Validators.MSG_REGEX_DEVICE_IMEI)
     */
    public static final String REGEX_REGEX_DEVICE_IMEI = "^[0-9]{15}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_DEVICE_IMEI: result.setCode(4533);break;
     */
    public static final String MSG_REGEX_DEVICE_IMEI = "设备IMEI码格式不对";


    /**
     * 正则表达式：验证deviceImsi
     * 验证规则: 15长度，只能是数字
     * 注解：@Pattern(regexp = Validators.REGEX_REGEX_DEVICE_IMSI ,message = Validators.MSG_REGEX_DEVICE_IMSI)
     */
    public static final String REGEX_REGEX_DEVICE_IMSI = "^[0-9]{15}$";

    /**
     * 错误返回信息
     * Exception代码：case Validators.MSG_REGEX_DEVICE_IMSI: result.setCode(4534);break;
     */
    public static final String MSG_REGEX_DEVICE_IMSI = "设备IMSI码格式不对";


    /**
     * 校验passwordP
     *
     * @param passwordP
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPasswordP(String passwordP) {
        return Pattern.matches(REGEX_PASSWORD_P, passwordP);
    }


    /**
     * 校验chEnNum
     *
     * @param chEnNum
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChEnNum(String chEnNum) {
        return Pattern.matches(REGEX_CH_EN_NUM, chEnNum);
    }


    /**
     * 校验usernameC
     *
     * @param usernameC
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsernameC(String usernameC) {
        return Pattern.matches(REGEX_USERNAME_C, usernameC);
    }


    /**
     * 校验usernameE
     *
     * @param usernameE
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsernameE(String usernameE) {
        return Pattern.matches(REGEX_USERNAME_E, usernameE);
    }


    /**
     * 校验usernameCE
     *
     * @param usernameCE
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsernameCE(String usernameCE) {
        return Pattern.matches(REGEX_USERNAME_C_E, usernameCE);
    }


    /**
     * 校验firmName
     *
     * @param firmName
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isFirmName(String firmName) {
        return Pattern.matches(REGEX_FIRM_NAME, firmName);
    }


    /**
     * 校验linkman
     *
     * @param linkman
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLinkman(String linkman) {
        return Pattern.matches(REGEX_LINKMAN, linkman);
    }


    /**
     * 校验position
     *
     * @param position
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPosition(String position) {
        return Pattern.matches(REGEX_POSITION, position);
    }


    /**
     * 校验projectName
     *
     * @param projectName
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isProjectName(String projectName) {
        return Pattern.matches(REGEX_REGEX_PROJECT_NAME, projectName);
    }


    /**
     * 校验address
     *
     * @param address
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isAddress(String address) {
        return Pattern.matches(REGEX_REGEX_ADDRESS, address);
    }


    /**
     * 校验addressCode
     *
     * @param addressCode
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isAddressCode(String addressCode) {
        return Pattern.matches(REGEX_REGEX_ADDRESS_CODE, addressCode);
    }


    /**
     * 校验personName
     *
     * @param personName
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPersonName(String personName) {
        return Pattern.matches(REGEX_REGEX_PERSON_NAME, personName);
    }


    /**
     * 校验tracking
     *
     * @param tracking
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isTracking(String tracking) {
        return Pattern.matches(REGEX_REGEX_TRACKING, tracking);
    }


    /**
     * 校验express
     *
     * @param express
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isExpress(String express) {
        return Pattern.matches(REGEX_REGEX_EXPRESS, express);
    }


    /**
     * 校验deviceNumber
     *
     * @param deviceNumber
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isDeviceNumber(String deviceNumber) {
        return Pattern.matches(REGEX_REGEX_DEVICE_NUMBER, deviceNumber);
    }


    /**
     * 校验deviceIccid
     *
     * @param deviceIccid
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isDeviceIccid(String deviceIccid) {
        return Pattern.matches(REGEX_REGEX_DEVICE_ICCID, deviceIccid);
    }


    /**
     * 校验deviceImei
     *
     * @param deviceImei
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isDeviceImei(String deviceImei) {
        return Pattern.matches(REGEX_REGEX_DEVICE_IMEI, deviceImei);
    }


    /**
     * 校验deviceImsi
     *
     * @param deviceImsi
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isDeviceImsi(String deviceImsi) {
        return Pattern.matches(REGEX_REGEX_DEVICE_IMSI, deviceImsi);
    }


    /**
     * 校验用户名
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }
    
    /**
     * 允许中文、数字、字符
     * 
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChineseOrEnglishOrNumber(String username) {
    	return Pattern.matches(REGEX_CHINESE_OR_ENGLISH_OR_NUMBER, username);
    }

    /**
     * 校验密码
     * 
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    
    /**
     * 校验密码是否有效
     * @param password
     * @return
     */
    public static boolean isEnglishOrNumber(String password) {
        return Pattern.matches(REGEX_ENGLISH_OR_NUMBER, password);
    }
 
    /**
     * 校验手机号
     * 
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPhone(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验数字
     *
     * @param number
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isNumber(String number) {
        return Pattern.matches(REGEX_NUMBER, number);
    }
 
    /**
     * 校验邮箱
     * 
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }
 
    /**
     * 校验汉字
     * 
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }
 
    /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
 
    /**
     * 校验URL
     * 
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUrl(String url) {
        return Pattern.matches(REGEX_URL, url);
    }
 
    /**
     * 校验IP地址
     * 
     * @param ipAddr
     * @return
     */
    public static boolean isIPAddr(String ipAddr) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr);
    }
}