package DNA.service;

import DNA.bean.Employee;
import DNA.bean.PageBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
public interface IEmployeeService extends IService<Employee> {

    /**
    * @Description 查询所有员工信息（分页）
    * @Return [currentPage, size, employee, beginDateScope]
    * @Author 脱氧核糖
    * @Date 2021/9/10 12:24
    */
    PageBean getEmployeesByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope);

    /**
    * @Description 查询所有员工信息
    * @Return [id]
    * @Author 脱氧核糖
    * @Date 2021/9/11 10:19
    */
    List<Employee> getEmployees(Integer id);

    /**
    * @Description 查询最大工号
    * @Return []
    * @Author 脱氧核糖
    * @Date 2021/9/10 16:07
    */
    String maxWorkId();

    /**
    * @Description 增加员工信息
    * @Return [employee]
    * @Author 脱氧核糖
    * @Date 2021/9/10 16:11
    */
    Boolean addEmployee(Employee employee);
}
