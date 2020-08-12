package com.fantasi.xxd.rpc.server;

import com.fantasi.xxd.rpc.serivce.IHiService;
import com.fantasi.xxd.rpc.serivce.impl.HiServiceImpl;
import com.fantasi.xxd.rpc.serivce.impl.HiServiceImpl2;
import com.fantasi.xxd.rpc.zk.IRegisterCenter;
import com.fantasi.xxd.rpc.zk.RegisterCenterImpl;

import java.io.IOException;

/**
 * @author xxd
 * @date 2020/7/26 19:42
 */
public class ServerDemo {

    public static void main(String[] args) throws IOException {
        IHiService iHiService = new HiServiceImpl();
        IHiService iHiService2 = new HiServiceImpl2();
//        RpcServer rpcServer = new RpcServer();
//        rpcServer.publisher(iHiService, 8888);
        IRegisterCenter iRegisterCenter = new RegisterCenterImpl();
        RpcServer rpcServer = new RpcServer(iRegisterCenter, "127.0.0.1:8888");
        rpcServer.bing(iHiService, iHiService2);
        rpcServer.publisher();
        System.in.read();
    }
}
