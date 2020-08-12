package com.fantasi.xxd.rmi.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author xxd
 * @date 2020/7/26 17:55
 */
public interface IHelloService extends Remote {

    /**
     * 输出消息
     * @param msg
     * @return
     */
    String sayHello(String msg) throws RemoteException;
}
