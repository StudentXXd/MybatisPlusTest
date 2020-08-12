package com.fantasi.xxd.rpc.handler;

import com.fantasi.xxd.rpc.request.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @author xxd
 * @date 2020/7/26 19:11
 */
public class ProcessorHandler implements Runnable {

    /**
     * socket连接对象
     */
    private Socket socket;

    private Map<String, Object> handlerMap;

//    /**
//     * 服务端发布的服务
//     */
//    private Object service;

    public ProcessorHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        //TODO 处理请求
        ObjectInputStream objectInputStream = null;
        try{
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Object result = invoke(rpcRequest);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            objectInputStream.close();
            objectOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (objectInputStream != null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Object invoke(RpcRequest rpcRequest) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] args = rpcRequest.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i=0; i < args.length; i++){
            types[i] = args[i].getClass();
        }
        String serverName = rpcRequest.getClassName();
        String version = rpcRequest.getVersion();
        if (!"".equals(version)){
            serverName = serverName + "-" + version;
        }
        //从handlerMap中，根据客户端请求的地址，去拿到相应的服务，通过反射发起调用
        Object server = handlerMap.get(serverName);
        Method method = server.getClass().getMethod(rpcRequest.getMethodName(), types);
        return method.invoke(server, args);
    }
}
