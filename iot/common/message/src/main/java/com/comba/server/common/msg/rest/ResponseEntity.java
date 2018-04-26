package com.comba.server.common.msg.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ResponseEntity 承载REST请求响应
 *
 * @author maozhihui
 * @create 2017-10-03 11:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseEntity<T> {
    private int status;
    private String msg;
    private T data;
}
