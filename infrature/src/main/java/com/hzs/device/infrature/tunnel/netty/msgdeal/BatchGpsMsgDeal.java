package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.common.msgin.msg.LocationMsg;
import com.hzs.device.infrature.common.utils.DigitalConvertUtils;
import com.hzs.device.infrature.tunnel.mysql.manager.GpsLocationManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/2/19
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Service
public class BatchGpsMsgDeal extends GpsMsgDeal{


    @Resource
    private GpsLocationManager gpsLocationManager;

    @Override
    public String getPoint() {

        return "0704";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {


        ConnectMsg connectMsg = convert(msg, context);
        if (connectMsg == null || StringUtils.isEmpty(connectMsg.getDeviceId())){

            log.warn("GpsMsgDeal cannot get device by connect id. msg:{}", msg);
            return false;
        }
        List<LocationMsg> locationMsgs = generate(msg, connectMsg.getDeviceId());
        gpsLocationManager.batchInsert(locationMsgs);
        return false;
    }

    private static List<LocationMsg> generate(List<Integer> msg, String deviceId){

        List<Integer> temp = msg.subList(13, msg.size() - 2);
        int gpsCount = (int) DigitalConvertUtils.convert(10, temp.get(0), temp.get(1));
        int type = Integer.valueOf(temp.get(2) + "", 10);
        log.info("BatchGpsMsgDeal generate, gpsCount:{}, type:{}, temp:{}, ", gpsCount, type, temp);

        if (gpsCount == 0){

            return null;
        }
        temp = temp.subList(3, temp.size());

        List<LocationMsg> locationMsgs = new ArrayList<>(gpsCount);
        for (int i = 0; i < gpsCount; i++){
            int dataSize = (int) DigitalConvertUtils.convert(10, temp.get(0), temp.get(1));
            List<Integer> data = temp.subList(2 + 8, dataSize + 2);
            temp = temp.subList(dataSize + 2, temp.size());
            log.info("dataSize:{}, \ndata:{}, \ntemp:{}", dataSize, data, temp);
            LocationMsg locationMsg =
                    generateLocationMsg(data, deviceId);
            locationMsgs.add(locationMsg);
        }
        return locationMsgs;
    }


    public static void main(String[] args) {

        List<Integer> msg = Arrays.asList(126,
                7,4,  0,151,
                7,3,80,0,66,130,  0, 52,
                0, 2,
                1,
                0,72,
                0,0,0,0, 0,12,0,0,  1,211,140,113,  6,205,144,61, 0,0,0,0,0,0,33,3,3,20,86,40,1,4,0,0,0,0,48,1,31,49,1,10,228,2,1,16,229,1,1,230,1,0,231,8,0,0,0,0,0,0,0,0,238,10,1,204,8,112,33,6,179,236,2,
                0,0, 72,
                0,0,0,0,0,12,0,0,1,211,140,113,6,205,144,61,0,0,0,0,0,0,33,3,3,21,22,88,1,4,0,0,0,0,48,1,31,49,1,10,228,2,0,24,229,1,1,230,1,0,231,8,0,0,0,0,0,0,0,0,238,10,1,204,8,112,33,6,179,236,2,0,
                15,126);

        List<LocationMsg> msgs = generate(msg, "test1");
        log.info("msgs:{}", msgs);
    }

}
