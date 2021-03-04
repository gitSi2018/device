package com.hzs.device.infrature.tunnel.mysql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HongZhenSi
 * @date: 2021/2/26
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceIdHeartbeatTime {

    private String deviceId;

    private Long time;
}
