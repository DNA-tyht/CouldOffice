package DNA.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 公共返回对象
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/8/22 13:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicResponse {
    private long code;
    private String message;
    private Object obj;

    /**
    * @Description 成功返回对象
    * @Return [message]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:27
    */
    public static PublicResponse success(String message) {
        return new PublicResponse(200, message, null);
    }
    public static PublicResponse success(String message, Object obj) {
        return new PublicResponse(200, message, obj);
    }

    /**
    * @Description 失败返回对象
    * @Return [message]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:29
    */
    public static PublicResponse error(String message) {
        return new PublicResponse(500, message, null);
    }
    public static PublicResponse error(String message, Object obj) {
        return new PublicResponse(500, message, obj);
    }
}
