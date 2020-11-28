package com.atguigu.springcloud.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    // 404 not_found
    private Integer code;
    private String message;
    private String serverPort;
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null,null);
    }

    public CommonResult(Integer code, String message,String serverPort) {
        this(code, message, serverPort,null);
    }





}
