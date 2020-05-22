package com.github.east196.rap;



import com.github.east196.core.api.Result;
import com.github.east196.core.util.Validators;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 自定义异常处理器
 *
 * @Author east196
 * @Date 2019
 */
@RestControllerAdvice({"com.github.east196"})
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidException(MethodArgumentNotValidException e){
        log.debug("exception:{}", e);
        Result<Object> result = new Result<>();
        //获取信息 default message [手机号码格式不对]
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        String defaultMessage = allErrors.get(0).getDefaultMessage();
        //根据defaultMessage 返回相应状态码
        result.setMessage(defaultMessage);
        switch (defaultMessage) {
            //传入参数异常
            case Validators.MSG_MOBILE: result.setCode(4501);break;
            case Validators.MSG_PASSWORD: result.setCode(4502);break;
            case Validators.MSG_PASSWORD_P: result.setCode(4503);break;
            case Validators.MSG_CH_EN_NUM: result.setCode(4504);break;
            case Validators.MSG_USERNAME_C: result.setCode(4505);break;
            case Validators.MSG_USERNAME_E: result.setCode(4506);break;
            case Validators.MSG_USERNAME_C_E: result.setCode(4507);break;
            case Validators.MSG_FIRM_NAME: result.setCode(4521);break;
            case Validators.MSG_LINKMAN: result.setCode(4522);break;
            case Validators.MSG_POSITION: result.setCode(4523);break;
            case Validators.MSG_REGEX_PROJECT_NAME: result.setCode(4524);break;
            case Validators.MSG_REGEX_ADDRESS: result.setCode(4525);break;
            case Validators.MSG_REGEX_ADDRESS_CODE: result.setCode(4526);break;
            case Validators.MSG_REGEX_PERSON_NAME: result.setCode(4527);break;
            case Validators.MSG_REGEX_TRACKING: result.setCode(4528);break;
            case Validators.MSG_REGEX_EXPRESS: result.setCode(4529);break;
            case Validators.MSG_REGEX_DEVICE_NUMBER: result.setCode(4531);break;
            case Validators.MSG_REGEX_DEVICE_ICCID: result.setCode(4532);break;
            case Validators.MSG_REGEX_DEVICE_IMEI: result.setCode(4533);break;
            case Validators.MSG_REGEX_DEVICE_IMSI: result.setCode(4534);break;
            //未知错误
            default : result.setCode(4500);result.setMessage("Valid验证未知错误");break;

        }
        log.debug("result:{}", result);
        return result;
    }

/*
    @ExceptionHandler(AuthenticationException.class)
    public Result<?> handleAuthException(AuthenticationException e){
        log.info("AuthenticationException:{}",e);
        if("Token失效，请重新登录!".equals(e.getMessage())){
            return new Result<>(9009,"Token失效，请重新登录!");
        }
        if("用户不存在!".equals(e.getMessage())){
            return new Result<>(6110,"用户不存在!");
        }
        if("token非法无效!".equals(e.getMessage())){
            return new Result<>(9008,"token非法无效!");
        }
        if("账号已被锁定,请联系管理员!".equals(e.getMessage())){
            return new Result<>(6103,"账号已被锁定,请联系管理员!");
        }
        return new Result<>(9000,"未知错误");
    }
*/

    @ExceptionHandler(ResultException.class)
    public Result<?> handleRunException(ResultException e){

        log.info("exception:{}", e);
        Result<Object> result = new Result<>();
        Integer code = e.getCode();
        log.info("code:{}", code);
        if (e.getMsg()!=null){
            if (e.getCode()==null){
                e.setCode(9000);
            }
            result.setCode(9000);
            result.setMessage(e.getMsg());
            log.info("result:{}", result);
            return result;
        }
        if(code!=null){
            result.setCode(code);
        }
        switch (code){
            //传入参数异常
            case 4001 : result.setMessage("传入参数有误");break;
            case 4002 : result.setMessage("暂无权限");break;
            case 4003 : result.setMessage("数据不存在");break;
            case 4004 : result.setMessage("参数不能为空");break;
            case 4005 : result.setMessage("参数不能为0");break;
            case 4006 : result.setMessage("传入参数类型不存在");break;
            //文件的上传下载
            case 4101 : result.setMessage("文件导入失败");break;
            //ValidatorsOld:数据校验的问题
            case 4501 : result.setMessage("请输入正确的手机号码");break;
            case 4502 : result.setMessage("密码格式不对");break;
            //服务端的问题
            //查询
            case 5101 : result.setMessage("未找到对应数据");break;
            case 5102 : result.setMessage("查询出错");break;
            case 5103 : result.setMessage("暂无数据");break;
            //添加
            case 5201 : result.setMessage("添加失败");break;
            //更新
            case 5301 : result.setMessage("操作失败");break;
            case 5302 : result.setMessage("操作失败,设备不存在");break;
            //删除
            case 5401 : result.setMessage("未找到删除数据");break;
            case 5402 : result.setMessage("删除失败");break;
            //登录
            case 6101 : result.setMessage("登录失败，用户不存在");break;
            case 6102 : result.setMessage("用户名或密码错误");break;
            case 6103 : result.setMessage("该账号已被封");break;
            case 6104 : result.setMessage("用户名或密码错误");break;
            case 6105 : result.setMessage("您还不是业主端用户");break;
            case 6106 : result.setMessage("您还不是工程端用户");break;
            case 6107 : result.setMessage("你是个什么客户端总得告诉我吧");break;
            case 6108 : result.setMessage("登录失败，你尚未入驻平台");break;
            case 6109 : result.setMessage("你尚未入驻平台");break;
            case 6110 : result.setMessage("用户不存在");break;
            //注册
            case 6201 : result.setMessage("注册失败");break;
            case 6202 : result.setMessage("注册失败，该账号已被注册");break;
            case 6203 : result.setMessage("该账号尚未注册，请先注册");break;
            case 6204 : result.setMessage("该账号已被注册，请直接登录");break;
            case 6205 : result.setMessage("该号码已被绑定");break;
            case 6206 : result.setMessage("该账号已经认证，请直接登录");break;
            //验证码
            case 6301 : result.setMessage("验证码错误，请重新输入");break;
            case 6302 : result.setMessage("验证码发送失败");break;
            case 6303 : result.setMessage("验证码已过期，请重新发送");break;
            //密码修改
            case 6401 : result.setMessage("密码修改失败");break;
            case 6402 : result.setMessage("令牌错误");break;
            case 6403 : result.setMessage("密码错误");break;
            case 6404 : result.setMessage("获取密码盐（salt)异常");break;
            //用户角色
            case 6501 : result.setMessage("用户角色不存在");break;

            //IM端异常
            //IM用户
            case 7101 : result.setMessage("注册IM用户失败");break;
            case 7102 : result.setMessage("保存IM用户失败");break;
            case 7103 : result.setMessage("IM用户已经存在");break;
            case 7104 : result.setMessage("用户未注册IM");break;
            //IM群组
            case 7201 : result.setMessage("注册IM群组失败");break;
            case 7202 : result.setMessage("保存IM群组失败");break;
            case 7203 : result.setMessage("IM群组已经存在");break;
            //IM用户群组关系
            case 7301 : result.setMessage("有成员已经加入过群组，请重新整理userIds");break;
            case 7302 : result.setMessage("加入群组失败");break;
            case 7303 : result.setMessage("退出群组失败");break;
            case 7304 : result.setMessage("更新群组信息失败");break;
            case 7305 : result.setMessage("解散群组失败");break;
            case 7306 : result.setMessage("操作类型未知");break;
            //IM服务器
            case 7401 : result.setMessage("IM服务器注册用户失败");break;
            case 7402 : result.setMessage("IM服务器创建群组失败");break;
            case 7403 : result.setMessage("IM服务器用户加入群组失败");break;
            case 7404 : result.setMessage("IM服务器用户退出群组失败");break;
            case 7405 : result.setMessage("IM服务器解散群组失败");break;
            case 7406 : result.setMessage("IM服务器更新群组信息失败");break;
            case 7407 : result.setMessage("IM服务器同步用户所属群信息失败");break;
            case 7408 : result.setMessage("IM服务端刷新用户信息失败");break;
            //二维码生成器
            case 8101 : result.setMessage("生成二维码失败");break;
            //校验发货设备数量
            case 8201 : result.setMessage("发货设备总数大于可发货设备数量");break;
            case 8202 : result.setMessage("发货烟感总数大于可发货烟感设备数量");break;
            case 8203 : result.setMessage("发货水感总数大于可发货水感设备数量");break;
            case 8204 : result.setMessage("发货气感总数大于可发货气感设备数量");break;
            case 8205 : result.setMessage("发货电感总数大于可发货电感设备数量");break;
            case 8206 : result.setMessage("发货灭火器总数大于可发货灭火器设备数量");break;
            case 8207 : result.setMessage("发货消防栓总数大于可发货消防栓设备数量");break;
            //项目特有异常
            case 9001 : result.setMessage("已有人去现场处理");break;
            case 9002 : result.setMessage("维保人员正在前往现场");break;
            case 9003 : result.setMessage("区域出错");break;
            case 9004 : result.setMessage("暂无数据");break;
            case 9005 : result.setMessage("统计成功");break;
            case 9006 : result.setMessage("部分信息存贮失败");break;
            case 9007 : result.setMessage("字段不存在");break;
            case 9008 : result.setMessage("token非法");break;
            case 9009 : result.setMessage("token失效,请重新登录");break;
            case 9010 : result.setMessage("极速注册");break;
            case 9011 : result.setMessage("token为空");break;
            case 9014 : result.setMessage("定向推送失败,用户设备ID为空");break;
            case 9015 : result.setMessage("项目组保存失败");break;
            case 9016 : result.setMessage("项目组关联人员失败");break;

            //未知错误
            default : result.setCode(5000);result.setMessage("未知错误");break;
        }
        log.info("result:{}", result);
        return result;
    }
}
