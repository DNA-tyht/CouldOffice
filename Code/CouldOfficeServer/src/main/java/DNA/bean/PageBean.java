package DNA.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 分页返回对象
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/9/10 12:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean {

    @ApiModelProperty(value = "总条数")
    private long total;

    @ApiModelProperty(value = "数据列表")
    private List<?> data;
}
