package com.fantasi.xxd.rpc.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @author xxd
 * @date 2020/7/27 21:36
 */
public class RegisterCenterImpl implements IRegisterCenter {

    private CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(ZkConfig.CONNECTION_STR)
                .sessionTimeoutMs(4000)
                .retryPolicy(new ExponentialBackoffRetry(1000,10))
                .build();
        curatorFramework.start();
    }

    /**
     * 注册服务名称和服务地址
     * @param serverName
     * @param serverAddress
     */
    @Override
    public void register(String serverName, String serverAddress) {
        //注册相应的服务
        String serverPath = ZkConfig.ZK_REGISTER_PATH + "/" + serverName;

        try {
            //判断 /registers/product-server是否存在，不存在则创建
            if (curatorFramework.checkExists().forPath(serverPath) == null){
                curatorFramework.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(serverPath, "0".getBytes());
            }
            String address = serverPath + "/" + serverAddress;
            String rsNode = curatorFramework.create().withMode(CreateMode.EPHEMERAL)
                    .forPath(address, "0".getBytes());
            System.out.println("服务注册成功：" + rsNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
