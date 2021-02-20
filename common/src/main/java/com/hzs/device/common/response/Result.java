package com.hzs.device.common.response;

import com.hzs.device.common.msgin.Base;
import lombok.Data;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
public class Result<T> extends Base {

    private int code;

    private String msg;

    private T data;


    private Result(){

    }

    private Result(int code, String msg, T data){

        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result succeed(){

        Result result = new Result();
        result.setCode(0);
        result.setMsg("succeed");
        return result;
    }

    public static <T> Result<T> succeed(T data){

        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMsg("succeed");
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed(ErrorEnum errorEnum){

        Result<T> result = new Result<>();
        result.setCode(errorEnum.getCode());
        result.setMsg(errorEnum.getMsg());
        return result;
    }

}
