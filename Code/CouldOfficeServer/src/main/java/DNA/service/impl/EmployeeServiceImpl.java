package DNA.service.impl;

import DNA.bean.Employee;
import DNA.bean.PageBean;
import DNA.mapper.EmployeeMapper;
import DNA.service.IEmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * @Description 查询所有员工信息（分页）
     * @Return [currentPage, size, employee, beginDateScope]
     * @Author 脱氧核糖
     * @Date 2021/9/10 12:24
     */
    @Override
    public PageBean getEmployeesByPage(Integer currentPage, Integer size, Employee employee, LocalDate[] beginDateScope) {
        //开启分页
        Page<Employee> page = new Page<>(currentPage, size);
        IPage<Employee> employeesByPage = employeeMapper.getEmployeesByPage(page, employee, beginDateScope);
        return new PageBean(employeesByPage.getTotal(), employeesByPage.getRecords());
    }

    /**
    * @Description 查询所有员工信息
    * @Return [id]
    * @Author 脱氧核糖
    * @Date 2021/9/11 10:19
    */
    @Override
    public List<Employee> getEmployees(Integer id) {
        return employeeMapper.getEmployees(id);
    }

    /**
    * @Description 查询最大工号
    * @Return []
    * @Author 脱氧核糖
    * @Date 2021/9/10 16:07
    */
    @Override
    public String maxWorkId() {
        List<Map<String, Object>> maps = employeeMapper.selectMaps(new QueryWrapper<Employee>().select("max(workId)"));
        return String.format("%08d", Integer.parseInt(maps.get(0).get("max(workId)").toString()) + 1);
    }

    /**
    * @Description 增加员工信息
    * @Return [employee]
    * @Author 脱氧核糖
    * @Date 2021/9/10 16:12
    */
    @Override
    public Boolean addEmployee(Employee employee) {
        //处理合同期限，保留两位小数
        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();
        long days = beginContract.until(endContract, ChronoUnit.DAYS);
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365.00)));
        return 1 == employeeMapper.insert(employee);
    }
}
