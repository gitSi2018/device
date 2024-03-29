package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.common.msgin.msg.LocationMsg;
import com.hzs.device.infrature.common.utils.DigitalConvertUtils;
import com.hzs.device.infrature.tunnel.mysql.manager.GpsLocationManager;
import com.hzs.device.infrature.tunnel.netty.msgout.CommonResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
public class GpsMsgDeal extends ConnectMsgDeal{


    @Resource
    private CommonResponse commonResponse;

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

        ConnectMsg connectMsg = convert(msg, context);
        if (connectMsg == null || StringUtils.isEmpty(connectMsg.getDeviceId())){

            log.warn("GpsMsgDeal cannot get device by connect id. msg:{}", msg);
            return false;
        }
        List<Integer> data = msg.subList(13, msg.size() - 2);
        gpsLocationManager.storeIfAbsent(generateLocationMsg(data, connectMsg.getDeviceId()));
        sendToDevice(commonResponse.getMsgData(connectMsg.getDeviceId(), msg.get(11), msg.get(12), 0x02, 0x00, 0)
                , connectMsg.getChannel());
        return true;
    }

    private static final BigDecimal DIVIDE = new BigDecimal(Math.pow(10, 6));



    public static LocationMsg generateLocationMsg(List<Integer> msg, String deviceId){


        log.info("generateLocationMsg deviceId:{},  msg:{}", deviceId, msg);
        LocationMsg locationMsg = new LocationMsg();
//        List<Integer> alarms = msg.subList(0, 4);
//        long alarmNumber = DigitalConvertUtils.convert(10, alarms.toArray(new Integer[4]));
//        locationMsg.setVibrationAlarm( (alarmNumber & Long.valueOf("100000000", 2)) != 0);

        List<Integer> lngs  = msg.subList(8, 12);
        List<Integer> lats = msg.subList(12, 16);

        long lng = DigitalConvertUtils.convert(10, lngs.toArray(new Integer[4]));
        long lat = DigitalConvertUtils.convert(10, lats.toArray(new Integer[4]));
        locationMsg.setDeviceId(deviceId);
        locationMsg.setLatBigDec(new BigDecimal(lat).divide(DIVIDE, 6, RoundingMode.HALF_UP));
        locationMsg.setLngBigDec(new BigDecimal(lng).divide(DIVIDE, 6, RoundingMode.HALF_UP));

        locationMsg.setGpsTime(generateTime(msg));
        locationMsg.setGpsDateTime(new Date(locationMsg.getGpsTime()));
        locationMsg.setPowerPercent(dealPowerPercent(msg));
        locationMsg.setVibrationAlarm(dealVibrationAlarm(msg));
        return locationMsg;

    }

    private static boolean dealVibrationAlarm(List<Integer> msg){

        List<Integer> additionalMsg =  msg.subList(28, msg.size());
        while (!CollectionUtils.isEmpty(additionalMsg) && additionalMsg.size() > 2) {

            int id = additionalMsg.get(0);
            int length = additionalMsg.get(1);
            if (id != 0xE7) {

                additionalMsg = additionalMsg.subList(2 + length, additionalMsg.size());
                continue;
            }
            Integer alarm1 = additionalMsg.get(2);
            Integer alarm2 = additionalMsg.get(3);
            return alarm1 == 0 && alarm2 == 1;
        }
        return false;
    }

    private static Integer dealPowerPercent(List<Integer> msg){

        List<Integer> additionalMsg =  msg.subList(28, msg.size());
        while (!CollectionUtils.isEmpty(additionalMsg) && additionalMsg.size() > 2) {

            int id = additionalMsg.get(0);
            int length = additionalMsg.get(1);
            if (id != 0xE4) {

                additionalMsg = additionalMsg.subList(2 + length, additionalMsg.size());
                continue;
            }
            return additionalMsg.get(3);
        }
        return null;
    }



    private static final String FORMAT = "yy-MM-DD-hh-mm-ss";
    private static Long generateTime(List<Integer> msg){

        List<Integer> temp = msg.subList(22, 28);
        return generateTime(temp, FORMAT);
    }

    public static Long generateTime(List<Integer> temp, String format){


        StringBuilder timeStr = new StringBuilder();
        for (int i = 0; i < 6; i++){

            Integer data = temp.get(i);
            String code = Integer.toUnsignedString(data, 16);
            if (code.length() < 2){
                code = "0" + code;
            }

            timeStr.append(code);
            if (i != 5){
                timeStr.append("-");
            }

        }
        log.info("GpsMsgDeal generateTime, temp:{}, timeStr:{}", temp, timeStr);

        try{
            DateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(timeStr.toString()).getTime();
        }catch(Exception e){

            log.error("generateTime failed. timeStr:{}", timeStr, e);
            return null;
        }
    }


    public static void main(String[] args) {

        List<Integer> temp = Arrays.asList(0x21, 0x03, 0x03, 0x14, 0x55, 0x45);
        Long time = generateTime(temp, FORMAT);
        Date date = new Date(time);
        log.info("time:{}, date:{}", time, date);
//        Integer[] lats = { 0x01, 0xd3, 0x8c, 0x71};
//        Integer[] lngs = { 0x06, 0xcd, 0x90, 0x3d};
        Integer[] lats = {1, 211, 140, 113};
        Integer[] lngs = {6, 205, 144, 61};
        long lng = DigitalConvertUtils.convert(10, lngs);
        long lat = DigitalConvertUtils.convert(10, lats);

        log.info("lng:{}, lat:{}", lng, lat);

//        List<Integer> msg = Arrays.asList(126,
//                2, 0,
//                0, 72,
//                7, 3, 80, 0, 66, 130, 0, 20,
//                0, 0, 1, 0,   0, 76, 0, 1,
//                1, 211, 140, 113,   6, 205, 144, 61,
//                0, 0,   0, 0,   0, 0,
//                33, 3, 3, 20, 85, 69,
//                1,  4,   0, 0, 0, 0,
//                48, 1, 31,
//                49, 1, 8,
//                228, 2, 1, 16,
//                229, 1, 1,
//                230, 1, 0,
//                231, 8, 0, 0, 0, 0, 0, 0, 0, 0,
//                238, 10, 1, 204, 8, 112, 33, 6, 179, 236, 2, 0,
//                201, 126);
        List<Integer> msg = Arrays.asList(
                0x7e,  0x02 ,
                0x00,  0x00 , 0x48  ,0x05 ,  0x11 ,  0x60 ,  0x00 ,  0x44 ,  0x64   ,  0x00 ,  0x0d
                ,0x00 ,0x00 ,0x00 ,0x00   ,0x00 ,0x0c ,0x00 ,0x02 ,  0x01 ,0x59 ,0x88 ,0x5e  ,0x06 ,0xcb ,0xf3 ,0x44   ,0x00 ,0x00   ,0x00 ,0x00   ,0x00 ,0x00
                ,0x20 ,0x08 ,0x07 ,0x18 ,0x07 ,0x22
                ,0x01 ,0x04  ,0x00 ,0x00 ,0x00 ,0x01   ,0x30 ,0x01 ,0x1c   ,0x31 ,0x01 ,0x09   ,0xe4 ,0x02 ,0x00 ,0x63  ,0xe5 ,0x01 ,0x00  ,0xe6 ,0x01 ,0x00    ,0xe7 ,0x08 ,0x00 ,0x01 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00 ,0x00  ,0xee ,0x0a  ,0x01 ,0xcc ,0x01 ,0x26 ,0x2c ,0x00 ,0x9a ,0x67 ,0x43 ,0x00
                ,0x1e ,0x7e
        );
        List<Integer> data = msg.subList(13, msg.size() - 2);
        LocationMsg  msg1 = generateLocationMsg(data, "test");

        log.info("msg1:{}", msg1);
    }
}
