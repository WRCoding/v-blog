package top.lpepsi.vblog.dto;

import top.lpepsi.vblog.vdo.ResultCode;

/**
 * @program: v-blog
 * @description: 响应数据
 * @author: 林北
 * @create: 2020-02-14 15:00
 **/
public class Response<T> {

    private Integer code;
    private String message;
    private T data;

    /**
    * @Description: 成功无返回数据
    * @Param: [resultCode]
    * @return: top.lpepsi.vblog.dto.Response<T>
    * @Author: 林北
    * @Date: 2020-02-14
    */
    public static <T> Response<T> success(){
        return new Response<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }
    /**
    * @Description: 成功且返回数据
    * @Param: [resultCode, data]
    * @return: top.lpepsi.vblog.dto.Response<T>
    * @Author: 林北
    * @Date: 2020-02-14
    */
    public static <T> Response<T> success(T data){
        return new Response<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),data);
    }

    /**
    * @Description: 失败无返回数据
    * @Param: [resultCode]
    * @return: top.lpepsi.vblog.dto.Response<T>
    * @Author: 林北
    * @Date: 2020-02-14
    */
    public static <T> Response<T> failure(){
        return new Response<T>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage());
    }
    /**
    * @Description: 失败且返回数据
    * @Param: [resultCode, data]
    * @return: top.lpepsi.vblog.dto.Response<T>
    * @Author: 林北
    * @Date: 2020-02-14
    */
    public static <T> Response<T> failure(T data){
        return new Response<T>(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage(),data);
    }

    public static <T> Response<T> customize(ResultCode resultCode,T data){
        return new Response<T>(resultCode.getCode(), resultCode.getMessage(),data);
    }

    public Response() {
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
