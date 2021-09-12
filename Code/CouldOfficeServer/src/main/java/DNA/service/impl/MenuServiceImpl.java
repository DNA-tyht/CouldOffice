package DNA.service.impl;

import DNA.bean.Admin;
import DNA.bean.Menu;
import DNA.mapper.MenuMapper;
import DNA.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @Description 通过用户id查询菜单列表
     * @Return []
     * @Author 脱氧核糖
     * @Date 2021/9/1 14:44
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis获取菜单数据
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        if (CollectionUtils.isEmpty(menus)) {
            //redis为空则去数据库获取，并将数据保存到redis中
            menus = menuMapper.getMenusByAdminId(adminId);
            valueOperations.set("menu_" + adminId, menus);
        }
        //从后端获取id
        return menus;
    }

    /**
    * @Description 根据角色获取菜单列表
    * @Return []
    * @Author 脱氧核糖
    * @Date 2021/9/2 12:31
    */
    @Override
    public List<Menu> getMenusByRole() {
        return menuMapper.getMenusByRole();
    }

    /**
     * @Description 查询所有菜单
     * @Return []
     * @Author 脱氧核糖
     * @Date 2021/9/5 15:51
     */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
