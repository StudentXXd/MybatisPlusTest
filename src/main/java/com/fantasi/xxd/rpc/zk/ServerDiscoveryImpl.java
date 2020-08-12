package com.fantasi.xxd.rpc.zk;

import com.fantasi.xxd.rpc.zk.IServerDiscovery;
import com.fantasi.xxd.rpc.zk.loadBalance.LoadBalance;
import com.fantasi.xxd.rpc.zk.loadBalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xxd
 * @date 2020/7/29 22:54
 */
public class ServerDiscoveryImpl implements IServerDiscovery {

    private List<String> repos = new ArrayList<>();

    private CuratorFramework curatorFramework;

    private String address;

    public ServerDiscoveryImpl(String address) {
        this.address = address;

        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(address)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000,10))
                .build();
        curatorFramework.start();
    }

    /**
     * 根据请求的服务地址，获得相应的调用地址
     * @param serverName
     * @return
     */
    @Override
    public String discovery(String serverName) {
        String path = ZkConfig.ZK_REGISTER_PATH + "/" + serverName;
        try {
            repos = curatorFramework.getChildren().forPath(path);

        } catch (Exception e) {
            throw new RuntimeException("获取子节点异常：" + e);
        }
        //动态发现服务节点的变化
        registerWatcher(path);
        //负载均衡机制
        LoadBalance loadBalance = new RandomLoadBalance();
        //返回调用的服务地址
        return loadBalance.selectHost(repos);
    }

    private void registerWatcher(String path){
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener pathChildrenCacheListener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                repos = curatorFramework.getChildren().forPath(path);
            }
        };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        try {
            pathChildrenCache.start();
        }catch (Exception e){
            throw new RuntimeException("注册PathChildren Watcher：" + e);
        }
    }
}
