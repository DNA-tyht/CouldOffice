package DNA.service.impl;

import DNA.bean.Salary;
import DNA.mapper.SalaryMapper;
import DNA.service.ISalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DNA
 * @since 2021-08-21
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

}
