package com.heqilin.util.plugin.cache;

import com.heqilin.util.LogUtil;
import com.heqilin.util.PropUtil;
import com.heqilin.util.SystemUtil;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;
import net.spy.memcached.internal.OperationFuture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class MemcachedImpl implements ICache {

    //region 初使化客户端

    private static MemcachedClient client=null;

    static{
        //加载配置文件
        Properties prop = PropUtil.getProp(SystemUtil.getMyUtilConfigPath());
        final String host = prop.getProperty("my.utils.cacheutil.memcached.host");//控制台上的“内网地址”
        final String port =prop.getProperty("my.utils.cacheutil.memcached.port"); //默认端口 11211，不用改
        final String username = prop.getProperty("my.utils.cacheutil.memcached.username"); //控制台上的“实例ID”，新版ocs的username可以置空
        final String password = prop.getProperty("my.utils.cacheutil.memcached.password"); //邮件中提供的“密码”
        AuthDescriptor ad = new AuthDescriptor(new String[]{"PLAIN"}, new PlainCallbackHandler(username, password));
        try {
            client = new MemcachedClient(
                    new ConnectionFactoryBuilder().setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                            .setAuthDescriptor(ad)
                            .build(),
                    AddrUtil.getAddresses(host + ":" + port));
        } catch (IOException ex) {
            LogUtil.error("",ex);
        }
    }
    //endregion

    @Override
    public boolean exists(String key) {
        return true;
    }

    @Override
    public void addCache(String cacheKey, Object objObject) {
        OperationFuture<Boolean> future = client.set(cacheKey,0,objObject);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addCache(String cacheKey, Object objObject, long cacheSeconds) {
        OperationFuture<Boolean> future = client.set(cacheKey,(int)cacheSeconds,objObject);
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCache(String cacheKey) {
        client.delete(cacheKey);
    }

    @Override
    public void removeAllCache() {
        client.flush();
    }

    @Override
    public <T> T getCache(String cacheKey) {
        //return  JsonUtil.instance.toBean(client.get(cacheKey),tClass);
        MemcachedSerializingTranscoder transcoder = new MemcachedSerializingTranscoder();
        return (T)client.get(cacheKey,transcoder);
    }

    public static void main(String[] args) {
        MemcachedImpl bll = new MemcachedImpl();
        System.out.println(bll.isConfigurationSuccess());
        List<String> result = new ArrayList<>();
        result.add("a");
        result.add("b");
        bll.addCache("heqilin",result);
        System.out.println(bll.getCache("heqilin").toString());
    }

}
