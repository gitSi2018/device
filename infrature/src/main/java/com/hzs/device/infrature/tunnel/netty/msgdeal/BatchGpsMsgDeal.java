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


        ConnectMsg connectMsg = getConnectMsg(context);
        if (connectMsg == null || StringUtils.isEmpty(connectMsg.getDeviceId())){

            log.warn("GpsMsgDeal cannot get device by connect id. msg:{}", msg);
            return false;
        }
        List<LocationMsg> locationMsgs = generate(msg, connectMsg.getDeviceId());
        gpsLocationManager.batchInsert(locationMsgs);
        return false;
    }

    private List<LocationMsg> generate(List<Integer> msg, String deviceId){

        List<Integer> temp = msg.subList(13, msg.size() - 2);
        int gpsCount = (int) DigitalConvertUtils.convert(temp.get(0), temp.get(1), 16);
        int type = Integer.valueOf(temp.get(2) + "", 16);
        log.info("BatchGpsMsgDeal generate, gpsCount:{}, type:{}, temp:{}, ", gpsCount, type, temp);

        if (gpsCount == 0){

            return null;
        }
        temp = temp.subList(3, temp.size());

        List<LocationMsg> locationMsgs = new ArrayList<>(gpsCount);
        for (int i = 0; i < gpsCount; i++){
            int dataSize = (int) DigitalConvertUtils.convert(temp.get(0), temp.get(1), 16);
            List<Integer> data = temp.subList(2, dataSize + 2);
            temp = temp.subList(dataSize + 2, temp.size());
            log.info("dataSize:{}, \ndata:{}, \ntemp:{}", dataSize, data, temp);
            LocationMsg locationMsg =
                    generateLocationMsg(data, deviceId);
            locationMsgs.add(locationMsg);
        }
        return locationMsgs;
    }


}
