package DNA.controller;

import DNA.bean.Department;
import DNA.bean.PublicResponse;
import DNA.service.IDepartmentService;
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
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @ApiOperation(value = "查询所有部门信息")
    @GetMapping("/")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @ApiOperation(value = "增加部门信息")
    @PostMapping("/")
    public PublicResponse addDepartment(@RequestBody Department department) {
        if (departmentService.addDepartment(department)) {
            return PublicResponse.success("增加成功");
        }
        return PublicResponse.error("增加失败");
    }

    @ApiOperation(value = "删除部门信息")
    @DeleteMapping("/{id}")
    public PublicResponse deleteDepartment(@PathVariable Integer id) {
        Integer result = departmentService.deleteDepartment(id);
        if (1 == result) {
            return PublicResponse.success("删除成功");
        } else if (-2 == result) {
            return PublicResponse.error("该部门下还有子部门，删除失败");
        } else if (-1 == result) {
            return PublicResponse.error("该部门下还有员工，删除失败");
        } else {
            return PublicResponse.error("删除失败");
        }
    }
}
