package com.fantasi.xxd.rpc.client;

import com.fantasi.xxd.rpc.handler.RpcClientProxy;
import com.fantasi.xxd.rpc.serivce.IHiService;
import com.fantasi.xxd.rpc.zk.IServerDiscovery;
import com.fantasi.xxd.rpc.zk.ServerDiscoveryImpl;
import com.fantasi.xxd.rpc.zk.ZkConfig;

/**
 * @author xxd
 * @date 2020/7/26 19:04
 */
public class ClientDemo {

    public static void main(String[] args) throws InterruptedException {
        IServerDiscovery iServerDiscovery = new ServerDiscoveryImpl(ZkConfig.CONNECTION_STR);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(iServerDiscovery);
        for (int i = 0; i < 10; i++){
            IHiService iHiService = rpcClientProxy.clientProxy(IHiService.class,"");
            System.out.println(iHiService.sayHi("xxd"));
            Thread.sleep(1000);
        }
    }
}
