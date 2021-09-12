package DNA.mapper;

import DNA.bean.AdminRole;
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
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    /**
     * @Description 修改操作员角色信息
     * @Return [adminId, rids]
     * @Author 脱氧核糖
     * @Date 2021/9/9 12:31
     */
    Integer addAdminRole(@Param("adminId") Integer adminId, @Param("rids") Integer[] rids);
}
