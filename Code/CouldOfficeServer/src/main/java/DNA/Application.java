package DNA;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/8/21 20:37
 */
@SpringBootApplication
@MapperScan("DNA.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
