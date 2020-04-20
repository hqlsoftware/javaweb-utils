package com.heqilin.util.core;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * HttpUtil
 *
 * @author heqilin
 * date 2019/04/12
 */
public class HttpUtil {

    private HttpUtil(){
        throw new AssertionError();
    }

    public static OkHttpClient okHttpClient = new OkHttpClient();

    private static void setHeader(Request.Builder builder,Map<String,String> header){
        if(header!=null && !header.isEmpty()){
            header.forEach((x,y)->{
                builder.header(x,y);
            });
        }
    }
    /**
     *  Post
     * @param url
     * @param dataJson
     * @return
     */
    public static String post(String url,String dataJson,Map<String,String> header){
        MediaType JSON = MediaType.parse("application/dataJson; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, dataJson);
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(body);
        setHeader(builder,header);
        Request request = builder.build();
        //同步阻塞线程
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            LogUtil.error("",e);
            return null;
        }
    }

    public static String get(String url, Map<String,String> data,Map<String,String> header){
        String urlAttach = RequestUtil.getQueryStringFromMap(data,null);
        if(StringUtil.isNotEmpty(urlAttach)){
            urlAttach = "?"+urlAttach;
        }
        Request.Builder builder = new Request.Builder().url(url + urlAttach)
                .get();
        setHeader(builder,header);
        Request request = builder.build();
        //同步阻塞线程
        try (Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            LogUtil.error("",e);
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(get("http://www.baidu.com/",null,null));
    }
}
