package my.utils.utils;

import my.utils.model.WebApiJsonMsg;

/**
 *
 *
 * @author heqilin
 * date:  2019-01-15 ok
 **/
public class WebApiJsonMsgUtil {

    //region 常用两种
    /**
     * 返回成功
     **/
    public static WebApiJsonMsg success(String msg, Object data) {
        return new WebApiJsonMsg()
                .setSuccess(true)
                .setErrorCode("")
                .setMessage(msg)
                .setData(data);
    }

    /**
     * 返回失败
     **/
    public static WebApiJsonMsg error(String msg, String errorCode, Object data) {
        return new WebApiJsonMsg()
                .setSuccess(false)
                .setErrorCode(errorCode)
                .setMessage(msg)
                .setData(data);
    }
    //endregion

    //region 常用errorCode枚举类型
    /**
     * 常用errorCode枚举类型
     **/
    private static enum ERRORCODE {

        /**
         * 无效授权
         **/
        invalidauth,

        /**
         * 本次操作失败，请重试
         **/
        error,

        /**
         * 无效参数
         **/
        invalidparamer,

        /**
         * 内部错误
         **/
        exception,

        /**
         * 返回空数据->列表页，详情页
         **/
        empty
    }
    //endregion

    //region 简单封装

    /**
     * 基本参数不能为空
     **/
    public static WebApiJsonMsg errorWithInvalidParamer(String msg, Object data) {
        if (StringUtil.isEmpty(msg))
            msg = "基本参数不能为空";


        return new WebApiJsonMsg()
                .setSuccess(false)
                .setData(data)
                .setMessage(msg)
                .setErrorCode(ERRORCODE.invalidparamer.toString());
    }

    /**
     * 本次操作失败，请重试
     **/
    public static WebApiJsonMsg errorWithOperationFailed(String msg,Object data) {
        if (StringUtil.isEmpty(msg))
            msg = "本次操作失败，请重试";

        return new WebApiJsonMsg()
                .setSuccess(false)
                .setData(data)
                .setMessage(msg)
                .setErrorCode(ERRORCODE.error.toString());
    }

    /**
     * 服务器内部出错
     **/
    public static WebApiJsonMsg errorWithInternalError(String msg, Object data) {
        if (StringUtil.isEmpty(msg))
            msg = "服务器内部出错";

        return new WebApiJsonMsg()
                .setSuccess(false)
                .setData(data)
                .setMessage(msg)
                .setErrorCode(ERRORCODE.exception.toString());
    }

    /**
     * 登录超时/授权失败
     **/
    public static WebApiJsonMsg errorWithInvalidAuthorization(String msg , Object data) {
        if (StringUtil.isEmpty(msg))
            msg = "登录超时/授权失败";

        return new WebApiJsonMsg()
                .setSuccess(false)
                .setData(data)
                .setMessage(msg)
                .setErrorCode(ERRORCODE.invalidauth.toString());
    }

    /**
     * 返回数据为空  用于列表页,详情页
     **/
    public static WebApiJsonMsg successWithEmptyData(String msg , Object data) {
        if (StringUtil.isEmpty(msg))
            msg = "返回数据为空";

        return new WebApiJsonMsg()
                .setSuccess(true)
                .setData(data)
                .setMessage(msg)
                .setErrorCode(ERRORCODE.empty.toString());
    }

    //endregion

    public static void main(String[] args) {
        System.out.println(error(null,null,new int[]{1,2,3}));
        System.out.println(errorWithInternalError(null,null));
        System.out.println(errorWithInvalidAuthorization(null,null));
        System.out.println(errorWithInvalidParamer(null,null));
        System.out.println(errorWithOperationFailed(null,null));
        System.out.println(success(null,null));
        System.out.println(successWithEmptyData(null,null));
    }

}
