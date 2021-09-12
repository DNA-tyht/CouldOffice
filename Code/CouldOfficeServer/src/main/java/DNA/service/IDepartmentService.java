package DNA.service;

import DNA.bean.Department;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface IDepartmentService extends IService<Department> {

    /**
    * @Description 查询所有部门
    * @Return []
    * @Author 脱氧核糖
    * @Date 2021/9/7 21:43
    */
    List<Department> getAllDepartments();

    /**
    * @Description 添加部门
    * @Return [department]
    * @Author 脱氧核糖
    * @Date 2021/9/7 22:13
    */
    Boolean addDepartment(Department department);

    /**
    * @Description 删除部门
    * @Return [id]
    * @Author 脱氧核糖
    * @Date 2021/9/7 22:38
     * @return
    */
    Integer deleteDepartment(Integer id);
}
