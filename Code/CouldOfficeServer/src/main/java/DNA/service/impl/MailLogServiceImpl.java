package DNA.service.impl;

import DNA.bean.MailLog;
import DNA.mapper.MailLogMapper;
import DNA.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
