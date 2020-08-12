package com.fantasi.xxd.rpc.zk.loadBalance;

import java.util.List;
import java.util.Random;

/**
 * @author xxd
 * @date 2020/7/29 23:23
 */
public class RandomLoadBalance extends AbstractLoadBalance {

    @Override
    protected String doSelect(List<String> repos) {
        int size = repos.size();
        Random random = new Random();
        return repos.get(random.nextInt(size));
    }
}
