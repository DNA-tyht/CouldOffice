package DNA.controller;


import DNA.bean.Nation;
import DNA.service.INationService;
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
@RequestMapping("/nation")
public class NationController {

    @Autowired
    private INationService nationService;

    @ApiOperation(value = "查询所有民族信息")
    @GetMapping("/")
    public List<Nation> getAllNation(){
        return nationService.list();
    }
}
