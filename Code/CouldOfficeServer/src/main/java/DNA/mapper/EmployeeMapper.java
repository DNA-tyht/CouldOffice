package DNA.mapper;

import DNA.bean.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    /**
    * @Description 查询所有员工信息（分页）
    * @Return [page, employee, beginDateScope]
    * @Author 脱氧核糖
    * @Date 2021/9/10 12:31
    */
    IPage<Employee> getEmployeesByPage(Page<Employee> page, @Param("employee") Employee employee, @Param("beginDateScope") LocalDate[] beginDateScope);

    /**
    * @Description 查询所有员工信息
    * @Return [id]
    * @Author 脱氧核糖
    * @Date 2021/9/11 10:20
    */
    List<Employee> getEmployees(@Param("id") Integer id);
}
