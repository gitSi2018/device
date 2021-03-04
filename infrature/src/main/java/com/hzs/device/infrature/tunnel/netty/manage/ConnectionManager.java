package com.hzs.device.infrature.tunnel.netty.manage;

import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.mysql.domain.DeviceIdHeartbeatTime;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    private static final Map<String, DeviceIdHeartbeatTime> heatBeatMap = new ConcurrentHashMap<>();

    public Map<String, ConnectMsg> getDeviceIdMap(){

        return deviceIdMap;
    }

    public  Map<String, String> getConnMap(){

        return connMap;
    }

    public Map<String, DeviceIdHeartbeatTime> getHeatBeatMap(){

        return heatBeatMap;
    }


    public void dealOffline(String connectId, boolean deviceOffline){

        if (StringUtils.isEmpty(connectId)){

            return;
        }
        ConnectMsg connectMsg = getConnectMsgInByConnectId(connectId);
        if (connectMsg == null){

            log.warn("ConnectionManager dealOffline connectMsg is null. connectId:{}", connectId);
            return;
        }
        if (!StringUtils.isEmpty(connectMsg.getDeviceId()) && deviceOffline) {
            deviceIdMap.remove(connectMsg.getDeviceId());
        }
        SocketChannel channel = connectMsg.getChannel();
        if (channel != null && channel.isActive()){

            log.info("ConnectionManager dealOffline, channel will be close. connectId:{}, channel:{}", connectId, channel);
            channel.close();
        }
        connMap.remove(connectId);
        heatBeatMap.remove(connectId);

    }

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

        log.info("ConnectionManager addConnectMsgInByConnectId connectId:{}, msgIn:{}", connectId,msgIn );
        deviceIdMap.put(msgIn.getDeviceId(), msgIn);
        addHeatBeatMap(connectId, new DeviceIdHeartbeatTime(msgIn.getDeviceId(), System.currentTimeMillis()));
        return connMap.put(connectId, msgIn.getDeviceId());
    }

    public boolean addHeatBeatMap(String connectId, DeviceIdHeartbeatTime deviceIdHeartbeatTime){

        log.info("ConnectionManager addHeatBeatMap, connectId:{}, deviceIdHeartbeatTime:{}", connectId, deviceIdHeartbeatTime);
        heatBeatMap.put(connectId, deviceIdHeartbeatTime);
        return true;
    }

}
