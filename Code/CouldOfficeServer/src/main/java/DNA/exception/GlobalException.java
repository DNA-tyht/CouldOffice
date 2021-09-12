package DNA.exception;

import DNA.bean.PublicResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Description 全局异常类
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/9/4 12:45
 */
//控制器的增强类
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public PublicResponse mySqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return PublicResponse.error("该数据有关联数据，操作失败");
        }
        return PublicResponse.error("数据库异常，操作失败");
    }
}
