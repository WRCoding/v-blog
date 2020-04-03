package top.lpepsi.vblog.vdo;

/**
 * @program: v-blog
 * @description: 响应码枚举
 * @author: 林北
 * @create: 2020-02-14 10:49
 **/
public enum ResultCode {
    /**
     * 成功
     */
    SUCCESS(200,"成功"),
    /**
     * 服务器错误
     */
    ERROR(500,"服务器出错"),
    /**
     * 路径错误
     */
    NOT_FOUND(404,"页面未找到"),
    /**
     * 没有权限
     */
    ACCESS_DENY(5001,"没有权限"),

    /**
     * 参数无效
     */
    INVALID_ARGUMENT(5007,"参数无效")
    ;
    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
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
}
