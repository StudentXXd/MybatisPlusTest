package com.fantasi.xxd.rpc.transport;

import com.fantasi.xxd.rpc.request.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author xxd
 * @date 2020/7/26 19:21
 */
public class TCPTransport {

//    private String host;
//
//    private int port;

    private String serverAddress;

    public TCPTransport(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    //    public TCPTransport(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }

    private Socket newSocket(){
        System.out.println("创建一个新的连接");
        Socket socket = null;
        try{
            String[] arrs = serverAddress.split(":");
            socket = new Socket(arrs[0], Integer.parseInt(arrs[1]));
            return socket;
        }catch (Exception e){
            throw new RuntimeException("连接建立失败");
        }
    }

    public Object send(RpcRequest rpcRequest){
        Socket socket = null;
        try{
            socket = newSocket();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    socket.getOutputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(
                    socket.getInputStream());
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            objectOutputStream.close();
            return object;
        }catch (Exception e){
            throw new RuntimeException("发起远程调用异常：", e);
        }finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
