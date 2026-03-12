package com.github.east196.rap.ip;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.east196.core.api.Result;
import com.github.east196.core.api.ResultUtil;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author east196
 */
@Slf4j
@RestController
@Tag(name = "IP接口")
@RequestMapping("/xboot/common/ip")
@Transactional
public class IpInfoController {

    @Autowired
    private IpInfoUtil ipInfoUtil;

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @Operation(summary = "IP及天气相关信息")
    public Result<Object> upload(HttpServletRequest request) {

        String result= ipInfoUtil.getIpWeatherInfo(ipInfoUtil.getIpAddr(request));
        return new ResultUtil<Object>().setData(result);
    }
}