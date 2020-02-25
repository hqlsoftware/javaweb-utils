package com.heqilin.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Function;

/**
 *  Request请求方法
 *
 * @author heqilin
 * date:  2019-01-18
 **/
public class RequestUtil {

    private RequestUtil(){
        throw new AssertionError();
    }

    //region 获取目录
    /**
     * 获取Web根目录 /
     **/
    public static String getWebRoot(String filePath){
        try {
            String path = RequestUtil.class.getClassLoader().getResource("").toURI().getPath();
            path = path.replace("/WEB-INF/classes/", "");
            path = path.replace("/target/classes/", "");
            path = path.replace("file:/", "");
            if(SystemUtil.isWindowsSystem()){
                path = StringUtil.removePrefixString(path,"/");
            }
            return StringUtil.isEmpty(filePath) ? path : path + File.separator + filePath;
        } catch (URISyntaxException var2) {
            throw new RuntimeException(var2);
        }
    }

    //endregion

    //region 获取请求的相关信息

    /**
     * 获取终端标识 User_Agent
     * @param request
     * @return
     */
    public static String getUserAgent(HttpServletRequest request){
        return  request.getHeader("User-Agent");
    }

    /**
     * 获取浏览器信息
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request){
        //获取浏览器信息
        Browser browser = UserAgent.parseUserAgentString(getUserAgent(request)).getBrowser();
        //获取浏览器版本号
        Version version = browser.getVersion(getUserAgent(request));
        String info = browser.getName() + " " + version.getVersion();
        return info;
    }

    /**
     * 获取操作系统信息
     * @param request
     * @return
     */
    public static String getOS(HttpServletRequest request){
        //获取操作系统信息
        OperatingSystem operatingSystem = UserAgent.parseUserAgentString(getUserAgent(request)).getOperatingSystem();
        String info = operatingSystem.getName() + " " + operatingSystem.getDeviceType().getName();
        return info;
    }


    //endregion

    //region response
    /**
     * 设置页面不被缓存
     * @param response
     */
    public static void setPageNoCache(HttpServletResponse response){
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    /**
     * reponse直接输出页面内容
     * @param response
     * @param statusCode
     * 状态码 比如：403
     * @param msg
     * 输出消息
     */
    public static void responsePrint(HttpServletResponse response,int statusCode,String msg){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(statusCode);
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print(msg);
        } catch (IOException e) {
            LogUtil.error(null,e);
        }finally {
            if(writer!=null){
                writer.close();
            }
        }
    }

    //endregion

    //region获取IP

    /**
     * 获取真实的IP地址
     * @param request
     * @return
     */
    public String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        System.out.println("x-forwarded-for ip: " + ip);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            System.out.println("Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            System.out.println("WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            System.out.println("HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
            System.out.println("X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            System.out.println("getRemoteAddr ip: " + ip);
        }
        System.out.println("获取客户端ip: " + ip);
        return ip;
    }
    //endregion

    //region 获取参数集合

    /**
     * 获取QueryString列表
     * @param request
     * @return
     */
    public static List<String> getQueryStringInfos(HttpServletRequest request){
        String str = request.getQueryString();
        if(StringUtil.isEmpty(str))
            return null;
        return Arrays.asList(str.split("&"));
    }

    /**
     * 获取FormInfo参数列表
     * @param request
     * @return
     */
    public static List<String> getParameterStringInfos(HttpServletRequest request){
        Map<String,String[]> map = request.getParameterMap();
        if(map==null || map.isEmpty())
            return null;

        List<String> result = new ArrayList<>();
        map.forEach((key,value)->{
            result.add(key+"="+StringUtil.join(value,",",0,value.length));
        });

        return result;
    }

    //endregion

    //region 常用转换

    /**
     * map转str
     * @param map
     * @return
     */
    public static String getQueryStringFromMap(Map map,Function<String,String> handleKey){
        if(map==null || map.isEmpty())
            return null;
        Set<String> keySet = map.keySet();
        //将set集合转换为数组
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //给数组排序(升序)
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keyArray.length; i++) {
            // 参数值为空，则不参与签名 这个方法trim()是去空格
            if (map.get(keyArray[i]).toString().trim().length() > 0) {
                if(handleKey!=null){
                    sb.append(handleKey.apply(keyArray[i])).append("=");
                }else{
                    sb.append(keyArray[i]).append("=");
                }
                try {
                    sb.append(URLEncoder.encode(map.get(keyArray[i]).toString().trim(),"utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if(i != keyArray.length-1){
                sb.append("&");
            }
        }
        return sb.toString();
    }

    /**
     * str转map
     * @param str
     * @return
     */
    public static Map getMapFromQueryString(String str){
        if(StringUtil.isEmpty(str)){
            return null;
        }
        //根据&截取
        String[] strings = str.split("&");
        //设置HashMap长度
        int mapLength = strings.length;
        //判断hashMap的长度是否是2的幂。
        if((strings.length % 2) != 0){
            mapLength = mapLength+1;
        }
        Map<String,String> map = new HashMap<>(mapLength);
        //循环加入map集合
        for (int i = 0; i < strings.length; i++) {
            //截取一组字符串
            String[] strArray = strings[i].split("=");
            //strArray[0]为KEY  strArray[1]为值
            try {
                map.put(strArray[0], URLDecoder.decode(strArray[1],"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    //endregion

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("aaa","1111");
        map.put("bbb","2222");
        System.out.println(getQueryStringFromMap(map,x->"#"+x+"#"));
        System.out.println(JsonUtil.INSTANCE.toJson(getMapFromQueryString("aaa=111111&bbb=44444")));
    }
}
