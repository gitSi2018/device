package com.hzs.device.domain.task;

import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.mysql.domain.DeviceIdHeartbeatTime;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: HongZhenSi
 * @date: 2021/2/22
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class ConnectionCheckTask {


    @Resource
    private ConnectionManager connectionManager;


    @Scheduled(fixedDelay = 100000L)
    public void checkOfflineDevice(){

        try {
            Map<String, DeviceIdHeartbeatTime> heatBeatMap = connectionManager.getHeatBeatMap();
            if (CollectionUtils.isEmpty(heatBeatMap.keySet())) {
                log.info("heatBeatMap is empty");
            }
            Map<String, String> offlineMap = new HashMap<>();
            Map<String, String> onlineMap = new HashMap<>();

            for (String key : heatBeatMap.keySet()) {
                log.info("heatBeatMap key:{}, value:{}", key, heatBeatMap.get(key));
                DeviceIdHeartbeatTime value = heatBeatMap.get(key);
                if (isOffline(value)) {
                    try {
                        log.info("checkDeadConnection device is offline. heatBeatMap key:{}, value:{}", key, heatBeatMap.get(key));
                        offlineMap.put(value.getDeviceId(), key);

                    } catch (Exception e) {

                        log.error("checkDeadConnection error. key:{}, value:{}", key, value, e);
                    }
                }else {

                    onlineMap.put(value.getDeviceId(), key);
                }
            }
            dealOffline(offlineMap, onlineMap);
        }catch (Throwable e){

            log.error("checkDeadConnection error ", e);
        }

    }


    private void dealOffline(Map<String, String> offlineMap, Map<String, String> onlineMap){

        Map<String, String> lastOfflineMap = offlineMap;
        // k deviceId  v connectId
        onlineMap.forEach((k, v)  -> {
            if (lastOfflineMap.get(k) != null){
                //这个不能下线
                lastOfflineMap.remove(k);
            }
        });
        //处理下线
        log.info("dealOffline will offline device. lastOfflineMap:{}", lastOfflineMap);
        lastOfflineMap.forEach((k, v) -> connectionManager.dealOffline(v, true));
    }

    private boolean isOffline(DeviceIdHeartbeatTime deviceIdHeartbeatTime){

        if (deviceIdHeartbeatTime == null || StringUtils.isEmpty(deviceIdHeartbeatTime.getDeviceId())){

            return true;
        }
        Long lastHeatBeatTime = deviceIdHeartbeatTime.getTime();
        return lastHeatBeatTime == null || System.currentTimeMillis() - lastHeatBeatTime > 900 * 1000;
    }
}
