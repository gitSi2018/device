package com.hzs.device.api;

import com.hzs.device.common.enums.MsgSendIDEnum;
import com.hzs.device.common.response.Result;
import com.hzs.device.infrature.tunnel.netty.MsgOutDeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @Resource
    private MsgOutDeal msgOutDeal;

    @GetMapping("send")
    public Result sendQueryLocationMsg(){

        return null;
    }

    @GetMapping("temp/control")
    public Integer tempLocationControl(@RequestParam("timeInterval") Integer timeInterval,
                                      @RequestParam("validTime") Integer validTime,
                                      @RequestParam("deviceId") String deviceId){

        Integer[] data = new Integer[6];
        int timeInterval1 = timeInterval & Integer.valueOf("11111111", 2);
        data[1] = timeInterval1;

        int timeInterval2 = timeInterval >> 8;
        data[0] = timeInterval2;

        int validTime1 = validTime & Integer.valueOf("11111111", 2);
        data[5] = validTime1;

        validTime = validTime >> 8;
        int validTime2 = validTime & Integer.valueOf("11111111", 2);
        data[4] = validTime2;

        validTime = validTime >> 8;
        int validTime3 = validTime & Integer.valueOf("11111111", 2);
        data[3] = validTime3;

        validTime = validTime >> 8;
        int validTime4 = validTime & Integer.valueOf("11111111", 2);
        data[2] = validTime4;

        for (int i = 0; i < data.length; i++){

            log.info("index{}:{}", i, Integer.toString(data[i], 16));
        }

        Integer result = msgOutDeal.sendMsg(deviceId, MsgSendIDEnum.TEMP_LOCATION_CONTROL, data);

        return result;
    }
}
