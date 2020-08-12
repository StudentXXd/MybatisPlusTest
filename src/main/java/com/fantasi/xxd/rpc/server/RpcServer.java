package com.fantasi.xxd.rpc.server;

import com.fantasi.xxd.rpc.anno.RpcAnnotation;
import com.fantasi.xxd.rpc.handler.ProcessorHandler;
import com.fantasi.xxd.rpc.zk.IRegisterCenter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xxd
 * @date 2020/7/26 19:05
 */
public class RpcServer {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 注册中心
     */
    private IRegisterCenter iRegisterCenter;

    /**
     * 服务发布地址
     */
    private String serverAddress;

    /**
     * 存放服务名称和服务对象之间的关系
     */
    private Map<String, Object> handlerMap = new HashMap<>();

    public RpcServer(IRegisterCenter iRegisterCenter, String serverAddress) {
        this.iRegisterCenter = iRegisterCenter;
        this.serverAddress = serverAddress;
    }

    /**
     * 绑定服务名称和服务对象
     * @param servers
     */
    public void bing(Object... servers){
        for (Object server : servers){
            RpcAnnotation annotation = server.getClass().getAnnotation(RpcAnnotation.class);
            String serverName = annotation.value().getName();
            String version = annotation.version();
            if (!"".equals(version)){
                serverName = serverName + "-" + version;
            }
            //绑定服务接口名称对应的服务
            handlerMap.put(serverName, server);
        }
    }

    public void publisher(){
        ServerSocket serverSocket = null;
        try{
            String[] addrs = serverAddress.split(":");
            //启动服务监听
            serverSocket = new ServerSocket(Integer.parseInt(addrs[1]));
            for (String interfaceName : handlerMap.keySet()){
                iRegisterCenter.register(interfaceName, serverAddress);
                System.out.println("注册服务成功：" + interfaceName + " -> " + serverAddress);
            }

            while (true){
                Socket socket = serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket, handlerMap));
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
