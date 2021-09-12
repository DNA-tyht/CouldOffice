package DNA.service;

import DNA.bean.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface IMenuRoleService extends IService<MenuRole> {

    /**
    * @Description 修改角色菜单
    * @Return [rid, mids]
    * @Author 脱氧核糖
    * @Date 2021/9/5 16:13
    */
    boolean updateMenuRole(Integer rid, Integer[] mids);
}
