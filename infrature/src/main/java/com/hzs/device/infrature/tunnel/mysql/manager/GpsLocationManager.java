package com.hzs.device.infrature.tunnel.mysql.manager;

import com.hzs.device.common.msgin.msg.LocationMsg;
import com.hzs.device.infrature.tunnel.mysql.domain.GpsLocation;
import com.hzs.device.infrature.tunnel.mysql.mapper.GpsLocationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/2/19
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class GpsLocationManager {


    @Resource
    private GpsLocationMapper gpsLocationMapper;


    public boolean storeIfAbsent(LocationMsg locationMsg){


        log.info("GpsLocationManager store locationMsg:{}", locationMsg);
        if (null != gpsLocationMapper.getExistRecordId(locationMsg.getDeviceId(), locationMsg.getGpsTime())){
            log.warn("GpsLocationManager store, record has exist");
            return true;
        }
        GpsLocation gpsLocation = convert(locationMsg);

        Date now = new Date();
        gpsLocation.setCreated(now);
        gpsLocation.setUpdated(now);
        gpsLocation.setIsDelete(false);
        if (gpsLocation.getGpsTime() == null){

            log.warn("GpsLocationManager store, gps time is null, will use current time:{}", now.getTime());
            gpsLocation.setGpsTime(now.getTime());
        }
        gpsLocationMapper.insertSelective(gpsLocation);
        return true;
    }

    public boolean batchInsert(List<LocationMsg> locationMsgs){

        log.info("GpsLocationManager batchInsert, locationMsgs:{}", locationMsgs);
        if (CollectionUtils.isEmpty(locationMsgs)){

            log.warn("GpsLocationManager batchInsert, locationMsgs is empty");
            return false;
        }
        for (LocationMsg locationMsg : locationMsgs){
            storeIfAbsent(locationMsg);
        }
        return true;
    }


    private LocationMsg convert(GpsLocation location){

        LocationMsg msg = new LocationMsg();
        BeanUtils.copyProperties(location, msg);
        return msg;
    }

    private GpsLocation convert(LocationMsg msg){

        GpsLocation location = new GpsLocation();
        BeanUtils.copyProperties(msg, location);
        return location;
    }

}
