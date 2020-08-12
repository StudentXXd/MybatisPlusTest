package com.fantasi.xxd.rmi.service.impl;

import com.fantasi.xxd.rmi.service.IHelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author xxd
 * @date 2020/7/26 17:57
 */
public class HelloServiceImpl extends UnicastRemoteObject implements IHelloService {

    public HelloServiceImpl() throws RemoteException{
        super();
    }

    /**
     * 输出消息
     * @param msg
     * @return
     */
    @Override
    public String sayHello(String msg) throws RemoteException{
        return "Hello, " + msg;
    }
}
