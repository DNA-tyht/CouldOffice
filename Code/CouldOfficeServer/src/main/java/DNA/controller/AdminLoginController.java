package DNA.controller;

import DNA.bean.Admin;
import DNA.bean.AdminLoginParam;
import DNA.bean.PublicResponse;
import DNA.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>
 *  登录控制器
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Api(tags = "LoginController")
@RestController
public class AdminLoginController {

    @Autowired
    private IAdminService adminService;

    @ApiOperation(value = "登录后返回token")
    @PostMapping("/login")
    public PublicResponse login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {
        return adminService.login(adminLoginParam.getUsername(),
                adminLoginParam.getPassword(), adminLoginParam.getCode(), request);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public PublicResponse logout() {
        //前端删除JWT请求头，后端只需要返回success
        return PublicResponse.success("注销成功");
    }

    @ApiOperation(value = "获取当前登录用户信息")
    @PostMapping("/admin/info")
    public Admin getAdminInfo(Principal principal) {
        if (principal == null)
            return null;
        String username = principal.getName();
        Admin admin = adminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(adminService.getRolesByAdminId(admin.getId()));
        return admin;
    }
}
