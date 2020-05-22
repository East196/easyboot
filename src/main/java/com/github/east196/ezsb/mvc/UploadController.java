//package com.github.east196.ezboot.web.common;
//
//
//import java.io.FileInputStream;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import com.github.east196.ezboot.XbootException;
//import com.github.east196.ezboot.core.result.Result;
//import com.github.east196.ezboot.core.result.ResultUtil;
//import com.github.east196.ezboot.oss.QiniuUtil;
//import com.github.east196.ezboot.web.common.ip.IpInfoUtil;
//import com.github.east196.ezboot.web.common.ratelimit.RedisRaterLimiter;
//
//import cn.hutool.core.util.StrUtil;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @author east196
// */
//@Slf4j
//@RestController
//@Api(description = "文件上传接口")
//@RequestMapping("/xboot/upload")
//@Transactional
//public class UploadController {
//
//    @Autowired
//    private RedisRaterLimiter redisRaterLimiter;
//
//    @Autowired
//    private QiniuUtil qiniuUtil;
//
//    @Autowired
//    private IpInfoUtil ipInfoUtil;
//
//    @RequestMapping(value = "/file",method = RequestMethod.POST)
//    @ApiOperation(value = "文件上传")
//    public Result<Object> upload(@RequestParam("file") MultipartFile file,
//                                 HttpServletRequest request) {
//
//        // IP限流 在线Demo所需 5分钟限1个请求
//        String token = redisRaterLimiter.acquireTokenFromBucket("upload:"+ipInfoUtil.getIpAddr(request), 1, 300000);
//        if (StrUtil.isBlank(token)) {
//            throw new XbootException("上传那么多干嘛，等等再传吧");
//        }
//
//        String result = null;
//        String fileName = qiniuUtil.renamePic(file.getOriginalFilename());
//        try {
//            FileInputStream inputStream = (FileInputStream) file.getInputStream();
//            //上传七牛云服务器
//            result = qiniuUtil.qiniuInputStreamUpload(inputStream,fileName);
//        } catch (Exception e) {
//            log.error(e.toString());
//            return new ResultUtil<Object>().setErrorMsg(e.toString());
//        }
//
//        return new ResultUtil<Object>().setData(result);
//    }
//}
