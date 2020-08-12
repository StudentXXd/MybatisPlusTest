package com.fantasi.xxd.rmi.server;

import com.fantasi.xxd.rmi.service.IHelloService;
import com.fantasi.xxd.rmi.service.impl.HelloServiceImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author xxd
 * @date 2020/7/26 18:03
 */
public class Server {

    public static void main(String[] args) {
        IHelloService iHelloService = null;
        try {
            iHelloService = new HelloServiceImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("rmi://127.0.0.1/Hello", iHelloService);
            System.out.println("服务启动成功");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
