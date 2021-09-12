package DNA.mapper;

import DNA.bean.Department;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    /**
     * @Description 查询所有部门
     * @Return []
     * @Author 脱氧核糖
     * @Date 2021/9/7 21:43
     */
    List<Department> getAllDepartments(Integer parentId);

    /**
     * @Description 添加部门
     * @Return [department]
     * @Author 脱氧核糖
     * @Date 2021/9/7 22:13
     */
    Boolean addDepartment(Department department);

    /**
    * @Description 删除部门
    * @Return [department]
    * @Author 脱氧核糖
    * @Date 2021/9/7 22:41
    */
    void deleteDepartment(Department department);
}
