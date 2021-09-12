package DNA.service.impl;

import DNA.bean.MenuRole;
import DNA.mapper.MenuRoleMapper;
import DNA.service.IMenuRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;

    /**
     * @Description 修改角色菜单
     * @Return [rid, mids]
     * @Author 脱氧核糖
     * @Date 2021/9/5 16:13
     */
    @Override
    @Transactional
    public boolean updateMenuRole(Integer rid, Integer[] mids) {
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
        if (null == mids || 0 == mids.length) {
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result == mids.length;
    }
}
