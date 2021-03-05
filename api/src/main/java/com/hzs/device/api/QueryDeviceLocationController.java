package com.hzs.device.api;

import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.common.response.Result;
import com.hzs.device.infrature.tunnel.netty.MsgOutDeal;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("device")
public class QueryDeviceLocationController {




    @Resource
    private ConnectionManager connectionManager;

    @Resource
    private MsgOutDeal msgOutDeal;


    @GetMapping("list")
    public Result<List<ConnectMsg>> queryDevice(@RequestParam(name = "deviceId", required = false) String deviceId){

        deviceId = preDealDeviceId(deviceId);
        Map<String, ConnectMsg>  connectMsgMap =
                connectionManager.getDeviceIdMap();
        if (CollectionUtils.isEmpty(connectMsgMap)){

            log.warn("QueryDeviceLocationController queryDevice, connectMsgMap is empty");
            return Result.succeed();
        }

        if (StringUtils.isEmpty(deviceId) ){

            return Result.succeed(convert(connectMsgMap));
        }
        ConnectMsg connectMsg = connectMsgMap.get(deviceId);
        if (connectMsg == null){

            log.warn("QueryDeviceLocationController queryDevice, cannot find connectMsg. deviceId:{}", deviceId);
            return Result.succeed();
        }
        return Result.succeed(Collections.singletonList(connectMsg));
    }




    private static String preDealDeviceId(String deviceId){

        if (StringUtils.isEmpty(deviceId)){

            return deviceId;
        }

        int length = deviceId.length();
        if (length >= 12){

            return deviceId;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 12 - length; i++){
            builder.append("0");
        }
        builder.append(deviceId);
        return builder.toString();
    }



    private List<ConnectMsg> convert(Map<String, ConnectMsg> msgMap){

        List<ConnectMsg> connectMsgs = new ArrayList<>();
        msgMap.forEach((k, v) -> connectMsgs.add(v));
        return connectMsgs;
    }

}
