package DNA.mapper;

import DNA.bean.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

    /**
    * @Description 查询所有操作员信息
    * @Return [keywords, id]
    * @Author 脱氧核糖
    * @Date 2021/9/8 17:15
    */
    List<Admin> getAllAdmins(@Param("keywords") String keywords, @Param("id") Integer id);
}
