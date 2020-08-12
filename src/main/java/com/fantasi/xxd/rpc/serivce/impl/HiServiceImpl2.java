package com.fantasi.xxd.rpc.serivce.impl;

import com.fantasi.xxd.rpc.anno.RpcAnnotation;
import com.fantasi.xxd.rpc.serivce.IHiService;

/**
 * @author xxd
 * @date 2020/7/26 19:01
 */
@RpcAnnotation(value = IHiService.class, version = "2.0")
public class HiServiceImpl2 implements IHiService {
    /**
     * Hi方法
     *
     * @param msg
     * @return
     */
    @Override
    public String sayHi(String msg) {
        return "Hi, " + msg + " -> 当前版本为2.0";
    }
}
