package com.hzs.device.api;

import com.hzs.device.common.enums.MsgSendIDEnum;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.common.response.ErrorEnum;
import com.hzs.device.common.response.Result;
import com.hzs.device.infrature.tunnel.netty.MsgOutDeal;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import com.hzs.device.domain.service.*;

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



    @Autowired
    private SendMsgToDeviceService sendMsgToDeviceService;

    @Resource
    private ConnectionManager connectionManager;

    @Resource
    private MsgOutDeal msgOutDeal;

    @GetMapping("location/query")
    public Integer sendQueryLocationMsg(@RequestParam("deviceId") String deviceId){

        deviceId = preDealDeviceId(deviceId);
        return msgOutDeal.sendMsg(deviceId, MsgSendIDEnum.QUERY_LOCATION, new Integer[0]);
    }

    @GetMapping("location/query/device")
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

    @GetMapping("location/temp/control")
    public Integer tempLocationControl(@RequestParam("timeInterval") Integer timeInterval,
                                      @RequestParam("validTime") Integer validTime,
                                      @RequestParam("deviceId") String deviceId){

        deviceId = preDealDeviceId(deviceId);
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

    @GetMapping("control")
    public Result  deviceControl(
            @RequestParam("deviceId") String deviceId,
            @RequestParam("order") Integer order){

        // order 3 - 12
        // 3-关机 4-复位 5-恢复出厂设置 6-关闭数据通信 7-关闭所有无线通信 10-关机（实际是关闭gprs数据通信和GPS）
        // 11-重置3年超长待机设备电量（重置开机次数) 12 找寻终端设备，设备收到指令后播放一段音乐 13-查询设备外电电压、电流
        if (order < 3 || order > 12 || order == 8 || order == 9){

            log.warn("deviceControl  invalid order, deviceId:{}, order:{}", deviceId, order);
            return Result.failed(ErrorEnum.INVALID_ORDER);
        }
        deviceId = preDealDeviceId(deviceId);
        Integer result = msgOutDeal.sendMsg(deviceId, MsgSendIDEnum.DEVICE_CONTROL, order);
        log.info("deviceControl deviceId:{}, order:{}, result:{}", deviceId, order, result);
        if (result == 0){
            return Result.succeed();
        }
        return Result.failed(result);
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

    public static void main(String[] args) {

        String device = preDealDeviceId("123456789");
        log.info("device:{}", device);

    }

    private List<ConnectMsg> convert(Map<String, ConnectMsg> msgMap){

        List<ConnectMsg> connectMsgs = new ArrayList<>();
        msgMap.forEach((k, v) -> connectMsgs.add(v));
        return connectMsgs;
    }

}
