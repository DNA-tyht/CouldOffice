package DNA.controller;

import DNA.bean.Menu;
import DNA.bean.MenuRole;
import DNA.bean.PublicResponse;
import DNA.bean.Role;
import DNA.service.IMenuRoleService;
import DNA.service.IMenuService;
import DNA.service.IRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/9/5 14:31
 */
@RestController
@RequestMapping("/permit")
public class PermitController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation(value = "查询所有权限信息")
    @GetMapping("/")
    public List<Role> getAllRoles() {
        return roleService.list();
    }

    @ApiOperation(value = "增加权限信息")
    @PostMapping("/")
    public PublicResponse addRole(@RequestBody Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        if (roleService.save(role)) {
            return PublicResponse.success("增加成功");
        }
        return PublicResponse.error("增加失败");
    }

    @ApiOperation(value = "删除权限信息")
    @DeleteMapping("/{rid}")
    public PublicResponse deleteRole(@PathVariable Integer rid) {
        if (roleService.removeById(rid)) {
            return PublicResponse.success("删除成功");
        }
        return PublicResponse.error("删除失败");
    }

    @ApiOperation(value = "查询所有菜单信息")
    @GetMapping("/menus")
    public List<Menu> getAllMenus() {
        return menuService.getAllMenus();
    }

    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("/mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable(value = "rid") Integer rid) {
        return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid)).stream()
                .map(MenuRole::getMid).collect(Collectors.toList());
    }

    @ApiOperation(value = "修改角色菜单")
    @PutMapping("/")
    public PublicResponse updateMenuRole(Integer rid, Integer[] mids) {
        if (menuRoleService.updateMenuRole(rid, mids)) {
            return PublicResponse.success("修改成功");
        }
        return PublicResponse.error("修改失败");
    }
}
