package com.hzs.device.domain.task;

import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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

    @Scheduled(fixedDelay = 30000L)
    public void statusPrint(){

        try {

            log.info("into ConnectionCheckTask statusPrint.");
            Map<String, String> connMap = connectionManager.getConnMap();
            Map<String, ConnectMsg> deviceIdMap = connectionManager.getDeviceIdMap();

            Map<String, Long> heatBeatMap = connectionManager.getHeatBeatMap();
//        connMap.forEach((k, v) -> log.info("key:{}, value:{}", k, v));
            if (CollectionUtils.isEmpty(connMap.keySet())){
                log.info("connMap is empty");
            }
            for (String key : connMap.keySet()) {
                log.info("connMap key:{}, value:{}", key, connMap.get(key));
            }


            if (CollectionUtils.isEmpty(deviceIdMap.keySet())){
                log.info("deviceIdMap is empty");
            }
            for (String key : deviceIdMap.keySet()) {
                log.info("deviceIdMap key:{}, value:{}", key, deviceIdMap.get(key));
            }


            if (CollectionUtils.isEmpty(heatBeatMap.keySet())){
                log.info("heatBeatMap is empty");
            }
            for (String key : heatBeatMap.keySet()) {
                log.info("heatBeatMap key:{}, value:{}", key, heatBeatMap.get(key));
            }
        }catch (Exception e){

            log.error("ConnectionCheckTask statusPrint error.", e);
        }
    }

    @Scheduled(fixedDelay = 100000L)
    public void checkDeadConnection(){

        try {
            Map<String, Long> heatBeatMap = connectionManager.getHeatBeatMap();
            if (CollectionUtils.isEmpty(heatBeatMap.keySet())) {
                log.info("heatBeatMap is empty");
            }
            for (String key : heatBeatMap.keySet()) {
                log.info("heatBeatMap key:{}, value:{}", key, heatBeatMap.get(key));
                Long value = heatBeatMap.get(key);
                if (isOffline(value)) {
                    try {
                        connectionManager.dealOffline(key);
                    } catch (Exception e) {

                        log.error("checkDeadConnection error. key:{}, value:{}", key, value, e);
                    }
                }
            }
        }catch (Throwable e){

            log.error("checkDeadConnection error ", e);
        }

    }

    private boolean isOffline(Long lastHeatBeatTime){

        return lastHeatBeatTime == null || System.currentTimeMillis() - lastHeatBeatTime > 120 * 1000;
    }
}
