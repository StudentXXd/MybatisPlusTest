package com.fantasi.xxd.rpc.handler;

import com.fantasi.xxd.rpc.zk.IServerDiscovery;

import java.lang.reflect.Proxy;

/**
 * @author xxd
 * @date 2020/7/26 19:15
 */
public class RpcClientProxy {

    private IServerDiscovery iServerDiscovery;

    public RpcClientProxy(IServerDiscovery iServerDiscovery) {
        this.iServerDiscovery = iServerDiscovery;
    }

//    /**
//     * 创建哭护短的远程连接，通过远程代理进行访问
//     * @param interfaceClass
//     * @param host
//     * @param port
//     * @param <T>
//     * @return
//     */
//    public <T> T clientProxy(final Class<T> interfaceClass, final String host, final int port){
//        //使用动态代理
//        return (T) Proxy.newProxyInstance(
//                interfaceClass.getClassLoader(),
//                new Class[]{interfaceClass},
//                new RemoteInvocationHandler(host, port));
//    }

    public <T> T clientProxy(final Class<T> interfaceClass, String version){
        //使用动态代理
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new RemoteInvocationHandler(iServerDiscovery, version));
    }
}
