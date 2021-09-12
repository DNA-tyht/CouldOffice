package DNA.controller;


import DNA.bean.PoliticsStatus;
import DNA.service.IPoliticsStatusService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/politics-status")
public class PoliticsStatusController {

    @Autowired
    private IPoliticsStatusService politicsStatusService;

    @ApiOperation(value = "查询所有政治面貌信息")
    @GetMapping("/")
    public List<PoliticsStatus> getAllPoliticsStatus() {
        return politicsStatusService.list();
    }
}
