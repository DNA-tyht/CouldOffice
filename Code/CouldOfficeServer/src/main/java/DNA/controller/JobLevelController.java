package DNA.controller;

import DNA.bean.JobLevel;
import DNA.bean.PublicResponse;
import DNA.service.IJobLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
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
@RequestMapping("/joblevel")
public class JobLevelController {

    @Autowired
    private IJobLevelService jobLevelService;

    @ApiOperation(value = "查询所有职称信息")
    @GetMapping("/")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.list();
    }

    @ApiOperation(value = "增加职称信息")
    @PostMapping("/")
    public PublicResponse addJobLevel(@RequestBody JobLevel jobLevel) {
        jobLevel.setCreateDate(LocalDateTime.now());
        if (jobLevelService.save(jobLevel)) {
            return PublicResponse.success("增加成功");
        }
        return PublicResponse.error("增加失败");
    }

    @ApiOperation(value = "修改职称信息")
    @PutMapping("/")
    public PublicResponse updateJobLevel(@RequestBody JobLevel jobLevel) {
        jobLevel.setCreateDate(LocalDateTime.now());
        if (jobLevelService.updateById(jobLevel)) {
            return PublicResponse.success("修改成功");
        }
        return PublicResponse.error("修改失败");
    }

    @ApiOperation(value = "删除职称信息")
    @DeleteMapping("/{id}")
    public PublicResponse deleteJobLevel(@PathVariable Integer id) {
        if (jobLevelService.removeById(id)) {
            return PublicResponse.success("删除成功");
        }
        return PublicResponse.error("删除失败");
    }

    @ApiOperation(value = "批量删除职称信息")
    @DeleteMapping("/")
    public PublicResponse deleteJobLevelsByIds(Integer[] ids) {
        if (jobLevelService.removeByIds(Arrays.asList(ids))) {
            return PublicResponse.success("删除成功");
        }
        return PublicResponse.error("删除失败");
    }
}
