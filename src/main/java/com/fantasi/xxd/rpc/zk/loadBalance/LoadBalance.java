package com.fantasi.xxd.rpc.zk.loadBalance;

import java.util.List;

/**
 * @author xxd
 * @date 2020/7/29 23:18
 */
public interface LoadBalance {

    String selectHost(List<String> repos);
}
