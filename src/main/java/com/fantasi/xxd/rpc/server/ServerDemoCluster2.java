package com.fantasi.xxd.rpc.server;

import com.fantasi.xxd.rpc.serivce.IHiService;
import com.fantasi.xxd.rpc.serivce.impl.HiServiceImpl;
import com.fantasi.xxd.rpc.serivce.impl.HiServiceImpl3;
import com.fantasi.xxd.rpc.zk.IRegisterCenter;
import com.fantasi.xxd.rpc.zk.RegisterCenterImpl;

import java.io.IOException;

/**
 * @author xxd
 * @date 2020/7/29 23:57
 */
public class ServerDemoCluster2 {

    public static void main(String[] args) throws IOException {
        IHiService iHiService = new HiServiceImpl3();
        IRegisterCenter iRegisterCenter = new RegisterCenterImpl();
        RpcServer rpcServer = new RpcServer(iRegisterCenter, "127.0.0.1:8889");
        rpcServer.bing(iHiService);
        rpcServer.publisher();
        System.in.read();
    }
}
