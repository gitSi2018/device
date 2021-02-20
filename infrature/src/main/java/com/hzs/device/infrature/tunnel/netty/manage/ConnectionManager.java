package com.hzs.device.infrature.tunnel.netty.manage;

import com.hzs.device.common.msgin.msg.ConnectMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class ConnectionManager {


    private static final Map<String, ConnectMsg> deviceIdMap = new ConcurrentHashMap<>();
    private static final Map<String, String> connMap = new ConcurrentHashMap<>();

    private static final Map<String, Long> heatBeatMap = new ConcurrentHashMap<>();

    public ConnectMsg getConnectMsgInByDeviceId(String deviceId){

        ConnectMsg connectMsg =  deviceIdMap.get(deviceId);

        log.info("ConnectionManager getConnectMsgInByDeviceId deviceId:{}, connectMsg:{} ", deviceId, connectMsg);
        return connectMsg;
    }

    public ConnectMsg getConnectMsgInByConnectId(String connectId){

        String deviceId = connMap.get(connectId);
        if (deviceId == null){

            log.warn("ConnectionManager getConnectMsgInByConnectId deviceId is empty, connectId:{} ", connectId);
            return null;
        }
        ConnectMsg connectMsg = deviceIdMap.get(deviceId);
        log.info("ConnectionManager getConnectMsgInByConnectId deviceId:{}, connectId:{}, connectMsg:{} ",
                deviceId, connectId, connectMsg);
        return connectMsg;
    }

    public ConnectMsg addConnectMsgInByDeviceId(String deviceId, ConnectMsg msgIn){

        return deviceIdMap.put(deviceId, msgIn);
    }


    public String addConnectMsgInByConnectId(String connectId, ConnectMsg msgIn){

        deviceIdMap.put(msgIn.getDeviceId(), msgIn);
        return connMap.put(connectId, msgIn.getDeviceId());
    }

    public boolean addHeatBeatMap(String connectId, Long beatTime){

        heatBeatMap.put(connectId, beatTime);
        return true;
    }

}
