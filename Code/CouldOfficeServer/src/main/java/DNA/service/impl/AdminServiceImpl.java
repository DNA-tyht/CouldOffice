package DNA.service.impl;

import DNA.bean.Admin;
import DNA.bean.AdminRole;
import DNA.bean.PublicResponse;
import DNA.bean.Role;
import DNA.config.security.JwtTokenUtil;
import DNA.mapper.AdminMapper;
import DNA.mapper.AdminRoleMapper;
import DNA.mapper.RoleMapper;
import DNA.service.IAdminService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AdminRoleMapper adminRoleMapper;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * @Description 登录后返回token
     * @Return [username, password, request]
     * @Author 脱氧核糖
     * @Date 2021/8/22 13:48
     */
    @Override
    public PublicResponse login(String username, String password, String code, HttpServletRequest request) {
        //String cpatcha = (String) request.getSession().getAttribute("cpatcha");
        //if (!(cpatcha != null && cpatcha.equalsIgnoreCase(code))) {
        //    return PublicResponse.error("验证码输入错误");
        //}
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (userDetails == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
            return PublicResponse.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()) {
            return PublicResponse.error("账号被禁用，请联系管理员");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                (userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return PublicResponse.success("登录成功", tokenMap);
    }

    /**
    * @Description 根据用户名获取用户
    * @Return [username]
    * @Author 脱氧核糖
    * @Date 2021/8/22 14:17
    */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>()
                .eq("username", username).eq("enabled", true));
    }

    /**
     * @Description 根据用户id查询角色列表
     * @Return [adminId]
     * @Author 脱氧核糖
     * @Date 2021/9/2 14:40
     */
    @Override
    public List<Role> getRolesByAdminId(Integer adminId) {
        return roleMapper.getRolesByAdminId(adminId);
    }

    /**
    * @Description 查询所有操作员信息
    * @Return [keywords]
    * @Author 脱氧核糖
    * @Date 2021/9/8 17:12
    */
    @Override
    public List<Admin> getAllAdmins(String keywords) {
        return adminMapper.getAllAdmins(keywords,
                ((Admin)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    /**
    * @Description 修改操作员角色信息
    * @Return [adminId, rids]
    * @Author 脱氧核糖
    * @Date 2021/9/9 12:31
    */
    @Override
    @Transactional
    public Boolean updateAdminRole(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
        return rids.length == adminRoleMapper.addAdminRole(adminId, rids);
    }
}
