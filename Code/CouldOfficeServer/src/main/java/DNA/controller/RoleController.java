package DNA.controller;


import DNA.bean.Role;
import DNA.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "查询所有角色信息")
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.list();
    }
}
