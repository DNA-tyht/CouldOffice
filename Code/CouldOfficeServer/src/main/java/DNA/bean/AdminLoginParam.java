package DNA.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description 用户登录类
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/8/22 13:34
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AdminLogin对象", description="AdminLogin对象")
public class AdminLoginParam {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String code;
}
