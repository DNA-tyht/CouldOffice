package DNA.service.impl;

import DNA.bean.Department;
import DNA.mapper.DepartmentMapper;
import DNA.service.IDepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * @Description 查询所有部门
     * @Return []
     * @Author 脱氧核糖
     * @Date 2021/9/7 21:43
     */
    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartments(-1);
    }

    /**
    * @Description 添加部门
    * @Return [department]
    * @Author 脱氧核糖
    * @Date 2021/9/7 22:13
    */
    @Override
    public Boolean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        return 1 == department.getResult();
    }

    @Override
    public Integer deleteDepartment(Integer id) {
        Department department = new Department();
        department.setId(id);
        departmentMapper.deleteDepartment(department);
        return department.getResult();
    }
}
