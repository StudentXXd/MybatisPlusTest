package com.fantasi.xxd.rpc.handler;

import com.fantasi.xxd.rpc.request.RpcRequest;
import com.fantasi.xxd.rpc.transport.TCPTransport;
import com.fantasi.xxd.rpc.zk.IServerDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xxd
 * @date 2020/7/26 19:18
 */
public class RemoteInvocationHandler implements InvocationHandler {

//    private String host;
//
//    private int port;

    private IServerDiscovery iServerDiscovery;

    private String version;

    /*public RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }*/

    public RemoteInvocationHandler(IServerDiscovery iServerDiscovery, String version) {
        this.iServerDiscovery = iServerDiscovery;
        this.version = version;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);
        rpcRequest.setVersion(version);
        //根据接口名称得到对应的服务地址
        String serverAddress = iServerDiscovery.discovery(rpcRequest.getClassName());
        //通过tcp传输协议进行传输
        TCPTransport tcpTransport = new TCPTransport(serverAddress);
        return tcpTransport.send(rpcRequest);
    }
}
