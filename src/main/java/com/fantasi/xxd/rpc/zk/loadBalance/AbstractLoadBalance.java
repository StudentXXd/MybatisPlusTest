package com.fantasi.xxd.rpc.zk.loadBalance;

import java.util.List;

/**
 * @author xxd
 * @date 2020/7/29 23:19
 */
public abstract class AbstractLoadBalance implements LoadBalance{

    @Override
    public String selectHost(List<String> repos) {
        if (repos == null || repos.size() == 0){
            return null;
        }
        if (repos.size() == 1){
            return repos.get(0);
        }
        return doSelect(repos);
    }

    protected abstract String doSelect(List<String> repos);
}
