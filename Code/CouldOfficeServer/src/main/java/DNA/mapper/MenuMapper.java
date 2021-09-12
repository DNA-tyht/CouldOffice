package DNA.mapper;

import DNA.bean.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * @Description 通过用户id查询菜单列表
     * @Return []
     * @Author 脱氧核糖
     * @Date 2021/9/1 14:44
     */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * @Description 根据角色获取菜单列表
     * @Return []
     * @Author 脱氧核糖
     * @Date 2021/9/2 12:31
     */
    List<Menu> getMenusByRole();

    /**
     * @Description 查询所有菜单
     * @Return []
     * @Author 脱氧核糖
     * @Date 2021/9/5 15:51
     */
    List<Menu> getAllMenus();
}
