package DNA.service;

import DNA.bean.Admin;
import DNA.bean.PublicResponse;
import DNA.bean.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface IAdminService extends IService<Admin> {

    /**
     * @Description 登录后返回token
     * @Return [username, password, request]
     * @Author 脱氧核糖
     * @Date 2021/8/22 13:48
     */
    PublicResponse login(String username, String password, String code, HttpServletRequest request);

    /**
     * @Description 根据用户名获取用户
     * @Return [username]
     * @Author 脱氧核糖
     * @Date 2021/8/22 14:17
     */
    Admin getAdminByUserName(String username);

    /**
    * @Description 根据用户id查询角色列表
    * @Return [adminId]
    * @Author 脱氧核糖
    * @Date 2021/9/2 14:40
    */
    List<Role> getRolesByAdminId(Integer adminId);

    /**
    * @Description 查询所有操作员信息
    * @Return [keywords]
    * @Author 脱氧核糖
    * @Date 2021/9/8 17:12
    */
    List<Admin> getAllAdmins(String keywords);

    /**
    * @Description 修改操作员角色信息
    * @Return [adminId, rids]
    * @Author 脱氧核糖
    * @Date 2021/9/9 12:30
    */
    Boolean updateAdminRole(Integer adminId, Integer[] rids);
}
