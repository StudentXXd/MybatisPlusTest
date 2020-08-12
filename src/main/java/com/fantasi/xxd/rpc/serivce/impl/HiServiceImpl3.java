package com.fantasi.xxd.rpc.serivce.impl;

import com.fantasi.xxd.rpc.anno.RpcAnnotation;
import com.fantasi.xxd.rpc.serivce.IHiService;

/**
 * @author xxd
 * @date 2020/7/26 19:01
 */
@RpcAnnotation(IHiService.class)
public class HiServiceImpl3 implements IHiService {
    /**
     * Hi方法
     *
     * @param msg
     * @return
     */
    @Override
    public String sayHi(String msg) {
        return "Hi, " + msg + " -> 地址是8889";
    }
}
