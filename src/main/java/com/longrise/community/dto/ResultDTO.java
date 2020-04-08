package com.longrise.community.dto;

import com.longrise.community.exception.CustomizeErrorCode;
import com.longrise.community.exception.CustomizeException;
import lombok.Data;

@Data
public class  ResultDTO<T> {
    private Integer code;
    private String message;
    private T data;
    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }
    public static ResultDTO errorOf(CustomizeException e) {
        return errorOf(e.getCode(),e.getMessage());
    }

    public static ResultDTO errorOf(CustomizeErrorCode sysError) {
        return errorOf(sysError.getCode(),sysError.getMessage());
    }
    public static ResultDTO okOf(){
        return errorOf(200,"请求成功");
    }
    public static ResultDTO okOf(Object t){
        ResultDTO resultDTO = errorOf(200, "请求成功");
        resultDTO.setData(t);
        return resultDTO;
    }
}
