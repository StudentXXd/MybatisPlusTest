package com.fantasi.xxd.rpc.zk;

/**
 * @author xxd
 * @date 2020/7/27 21:35
 */
public interface IRegisterCenter {

    /**
     * 注册服务名称和服务地址
     * @param serverName
     * @param serverAddress
     */
    void register(String serverName, String serverAddress);
}
