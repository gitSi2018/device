package com.hzs.device;

import com.hzs.device.domain.setup.DeviceNettyInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */

@SpringBootApplication(scanBasePackages = {"com.hzs.device"})
@MapperScan(basePackages = "com.hzs.device.infrature.tunnel.mysql.mapper")
public class Application implements CommandLineRunner {




    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    private DeviceNettyInit deviceNettyInit;

    @Override
    public void run(String... args) throws Exception {

        deviceNettyInit.start(8080);
    }
}
