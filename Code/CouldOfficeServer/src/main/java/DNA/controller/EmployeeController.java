package DNA.controller;

import DNA.bean.*;
import DNA.service.*;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
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
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private INationService nationService;
    @Autowired
    private IPoliticsStatusService politicsStatusService;
    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private IJobLevelService jobLevelService;
    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "查询所有员工信息（分页）")
    @GetMapping("/")
    public PageBean getEmployees(@RequestParam(defaultValue = "1") Integer currentPage,
                                 @RequestParam(defaultValue = "10") Integer size,
                                 Employee employee, LocalDate[] beginDateScope) {
        return employeeService.getEmployeesByPage(currentPage, size, employee, beginDateScope);
    }

    @ApiOperation(value = "获取工号")
    @GetMapping("/maxWorkID")
    public PublicResponse maxWorkID(){
        String workId = employeeService.maxWorkId();
        return PublicResponse.success(null, workId);
    }

    @ApiOperation(value = "增加员工信息")
    @PostMapping("/")
    public PublicResponse addEmployee(@RequestBody Employee employee) {
        if (employeeService.addEmployee(employee)) {
            return PublicResponse.success("增加成功");
        }
        return PublicResponse.error("增加失败");
    }

    @ApiOperation(value = "修改员工信息")
    @PutMapping("/")
    public PublicResponse updateEmployee(@RequestBody Employee employee) {
        if (employeeService.updateById(employee)) {
            return PublicResponse.success("修改成功");
        }
        return PublicResponse.error("修改失败");
    }

    @ApiOperation(value = "删除员工信息")
    @DeleteMapping("/{id}")
    public PublicResponse deleteEmployee(@PathVariable Integer id) {
        if (employeeService.removeById(id)) {
            return PublicResponse.success("删除成功");
        }
        return PublicResponse.error("删除失败");
    }

    @ApiOperation(value = "导出员工数据")
    @GetMapping(value = "/export", produces = "application/octet-stream")
    public void exportEmployee(HttpServletResponse response) {
        List<Employee> employees = employeeService.getEmployees(null);
        //导出Excel表
        ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(params, Employee.class, employees);
        ServletOutputStream outputStream = null;
        try {
            //使用流形式传出
            response.setHeader("content-type", "application/octet-stream");
            //防止中文乱码
            response.setHeader("content-disposition", "attachment;filename="
                    + URLEncoder.encode("员工表.xls", "UTF-8"));
            outputStream = response.getOutputStream();
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public PublicResponse importEmployee(MultipartFile file) {
        ImportParams params = new ImportParams();
        //去掉标题行
        params.setTitleRows(1);
        //行数据
        List<Nation> nationList = nationService.list();
        List<PoliticsStatus> politicsStatusList = politicsStatusService.list();
        List<Department> departmentList = departmentService.list();
        List<JobLevel> jobLevelList = jobLevelService.list();
        List<Position> positionList = positionService.list();
        try {
            List<Employee> employees = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
            employees.forEach(employee -> {
                employee.setNationId(nationList.get(nationList.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setPoliticId(politicsStatusList.get(politicsStatusList.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                employee.setDepartmentId(departmentList.get(departmentList.indexOf(new Department(employee.getDepartment().getName()))).getId());
                employee.setJobLevelId(jobLevelList.get(jobLevelList.indexOf(new JobLevel(employee.getJobLevel().getName()))).getId());
                employee.setPosId(positionList.get(positionList.indexOf(new Position(employee.getPosition().getName()))).getId());
            });
            if (employeeService.saveBatch(employees)) {
                return PublicResponse.success("导入成功");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return PublicResponse.error("导入失败");
    }


}