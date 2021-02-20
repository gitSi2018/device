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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/1/29
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Service
public class GpsMsgDeal extends MsgDealServiceAbstract{


    @Resource
    private GpsLocationManager gpsLocationManager;

    @Override
    public String getPoint() {
        return "0200";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {

        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("GpsMsgDeal, msg:{}, context:{}", msg, context);

        ConnectMsg connectMsg = getConnectMsg(context);
        if (connectMsg == null || StringUtils.isEmpty(connectMsg.getDeviceId())){

            log.warn("GpsMsgDeal cannot get device by connect id. msg:{}", msg);
            return false;
        }
        List<Integer> data = msg.subList(21, msg.size() - 2);
        gpsLocationManager.store(generateLocationMsg(data, connectMsg.getDeviceId()));
        return true;
    }

    private static final BigDecimal DIVIDE = new BigDecimal(100000);

    public LocationMsg generateLocationMsg(List<Integer> msg, String deviceId){

        List<Integer> lngs  = msg.subList(0, 4);
        List<Integer> lats = msg.subList(4, 8);

        long lng = DigitalConvertUtils.convert(16, lngs.toArray(new Integer[4]));
        long lat = DigitalConvertUtils.convert(16, lats.toArray(new Integer[4]));
        LocationMsg locationMsg = new LocationMsg();
        locationMsg.setDeviceId(deviceId);
        locationMsg.setLatBigDec(new BigDecimal(lat).divide(DIVIDE, 6, RoundingMode.HALF_UP));
        locationMsg.setLngBigDec(new BigDecimal(lng).divide(DIVIDE, 6, RoundingMode.HALF_UP));

        locationMsg.setHigh((int)DigitalConvertUtils.convert(16, msg.get(8), msg.get(9)));
        locationMsg.setSpeed((int)DigitalConvertUtils.convert(16, msg.get(10), msg.get(11)));

        locationMsg.setDirect((int)DigitalConvertUtils.convert(16, msg.get(12), msg.get(13)));

        locationMsg.setGpsTime(generateTime(msg));
        return locationMsg;

    }



    private static final String FORMAT = "YY-MM-DD-hh-mm-ss";
    private Long generateTime(List<Integer> msg){

        List<Integer> temp = msg.subList(35, 41);
        StringBuilder timeStr = new StringBuilder();
        for (int i = 0; i < 6; i++){

            int data = temp.get(i);
            if (data < 10) {
                timeStr.append(0);
            }
            timeStr.append(data);
            if (i != 5){
                timeStr.append("-");
            }

        }
        log.info("GpsMsgDeal generateTime, temp:{}, timeStr:{}", temp, timeStr);

        try{
            DateFormat dateFormat = new SimpleDateFormat(FORMAT);
            return dateFormat.parse(timeStr.toString()).getTime();
        }catch(Exception e){

            log.error("generateTime failed. timeStr:{}", timeStr, e);
            return null;
        }
    }

    public static Long generateTimeTest(List<Integer> temp){

        StringBuilder timeStr = new StringBuilder();
        for (int i = 0; i < 6; i++){

            int data = temp.get(i);
            if (data < 10) {
                timeStr.append(0);
            }
            timeStr.append(data);
            if (i != 5){
                timeStr.append("-");
            }

        }
        log.info("GpsMsgDeal generateTime, temp:{}, timeStr:{}", temp, timeStr);

        try{
            DateFormat dateFormat = new SimpleDateFormat(FORMAT);
            return dateFormat.parse(timeStr.toString()).getTime();
        }catch(Exception e){

            log.error("generateTime failed. timeStr:{}", timeStr, e);
            return null;
        }
    }


    public static void main(String[] args) {

        List<Integer> temp = Arrays.asList(20, 8, 7, 18, 25, 57);
        Long time = generateTimeTest(temp);
        Date date = new Date(time);
        log.info("time:{}, date:{}", time, date);
    }
}
