package com.fantasi.xxd.rmi.client;

import com.fantasi.xxd.rmi.service.IHelloService;
import com.fantasi.xxd.rmi.service.impl.HelloServiceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author xxd
 * @date 2020/7/26 17:58
 */
public class ClientDemo {

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
//        IHelloService iHelloService = new HelloServiceImpl();
//        String msg = iHelloService.sayHello("xxd");
//        System.out.println(msg);
        IHelloService iHelloService =
                (IHelloService) Naming.lookup("rmi://127.0.0.1/Hello");
        String msg = iHelloService.sayHello("xxd");
        System.out.println(msg);
    }
}
