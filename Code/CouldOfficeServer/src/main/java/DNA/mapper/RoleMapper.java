package DNA.mapper;

import DNA.bean.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * @Description 根据用户id查询角色列表
     * @Return [adminId]
     * @Author 脱氧核糖
     * @Date 2021/9/2 14:40
     */
    List<Role> getRolesByAdminId(Integer adminId);
}
