package com.fantasi.xxd.rpc.zk;

/**
 * @author xxd
 * @date 2020/7/29 22:53
 */
public interface IServerDiscovery {

    /**
     * 根据请求的服务地址，获得相应的调用地址
     * @param serverName
     * @return
     */
    String discovery(String serverName);
}
