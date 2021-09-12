package DNA.controller;

import DNA.bean.Admin;
import DNA.bean.PublicResponse;
import DNA.service.IAdminService;
import DNA.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;
    @Autowired
    private IRoleService roleService;

    @ApiOperation(value = "查询所有操作员信息")
    @GetMapping("/")
    public List<Admin> getAllAdmins(String keywords) {
        return adminService.getAllAdmins(keywords);
    }

    @ApiOperation(value = "修改操作员信息")
    @PutMapping("/")
    public PublicResponse updateAdmin(@RequestBody Admin admin) {
        if (adminService.updateById(admin)) {
            return PublicResponse.success("修改成功");
        }
        return PublicResponse.error("修改成功");
    }

    @ApiOperation(value = "删除操作员信息")
    @DeleteMapping("/{id}")
    public PublicResponse deleteAdmin(@PathVariable Integer id) {
        if (adminService.removeById(id)) {
            return PublicResponse.success("删除成功");
        }
        return PublicResponse.error("删除失败");
    }

    @ApiOperation(value = "修改操作员角色信息")
    @PutMapping("/role")
    public PublicResponse updateAdminRole(Integer adminId, Integer[] rids) {
        if (adminService.updateAdminRole(adminId, rids)) {
            return PublicResponse.success("修改成功");
        }
        return PublicResponse.error("修改失败");
    }
}
