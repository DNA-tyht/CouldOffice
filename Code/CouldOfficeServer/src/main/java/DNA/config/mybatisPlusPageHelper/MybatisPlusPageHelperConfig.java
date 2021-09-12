package DNA.config.mybatisPlusPageHelper;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/9/10 12:02
 */
@Configuration
public class MybatisPlusPageHelperConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
