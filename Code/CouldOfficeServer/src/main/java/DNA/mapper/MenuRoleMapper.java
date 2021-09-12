package DNA.mapper;

import DNA.bean.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
    * @Description 更新角色菜单
    * @Return [rid, mids]
    * @Author 脱氧核糖
    * @Date 2021/9/5 16:26
    */
    Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
