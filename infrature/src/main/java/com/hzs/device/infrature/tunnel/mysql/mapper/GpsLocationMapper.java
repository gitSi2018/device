package com.hzs.device.infrature.tunnel.mysql.mapper;

import com.hzs.device.infrature.tunnel.mysql.domain.GpsLocation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author: HongZhenSi
 * @date: 2021/2/19
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
public interface GpsLocationMapper extends Mapper<GpsLocation> {


    @Select("select id from hz_gps_location where device_id = #{deviceId} and gps_time = #{gpsTime}")
    Long getExistRecordId(@Param("deviceId") String deviceId, @Param("gpsTime") Long gpsTime);
}
