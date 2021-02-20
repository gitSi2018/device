package com.hzs.device.api;

import com.hzs.device.common.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import com.hzs.device.domain.service.*;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("device/location")
public class QueryDeviceLocationController {



    @Autowired
    private SendMsgToDeviceService sendMsgToDeviceService;

    @GetMapping("send")
    public Result sendQueryLocationMsg(){

        return null;
    }
}
