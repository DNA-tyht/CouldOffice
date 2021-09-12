package DNA.controller;

import DNA.bean.Position;
import DNA.bean.PublicResponse;
import DNA.service.IPositionService;
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
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "查询所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions() {
        return positionService.list(); //mybatis-plus封装好了
    }

    @ApiOperation(value = "增加职位信息")
    @PostMapping("/")
    public PublicResponse addPosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.save(position)) {
            return PublicResponse.success("增加成功");
        }
        return PublicResponse.error("增加失败");
    }

    @ApiOperation(value = "修改职位信息")
    @PutMapping("/")
    public PublicResponse updatePosition(@RequestBody Position position) {
        position.setCreateDate(LocalDateTime.now());
        if (positionService.updateById(position)) {
            return PublicResponse.success("修改成功");
        }
        return PublicResponse.error("修改失败");
    }

    @ApiOperation(value = "删除职位信息")
    @DeleteMapping("/{id}")
    public PublicResponse deletePosition(@PathVariable Integer id) {
        if (positionService.removeById(id)) {
            return PublicResponse.success("删除成功");
        }
        return PublicResponse.error("删除失败");
    }

    @ApiOperation(value = "批量删除职位信息")
    @DeleteMapping("/")
    public PublicResponse deletePositionsByIds(Integer[] ids) {
        if (positionService.removeByIds(Arrays.asList(ids))) {
            return PublicResponse.success("删除成功");
        }
        return PublicResponse.error("删除失败");
    }
}
