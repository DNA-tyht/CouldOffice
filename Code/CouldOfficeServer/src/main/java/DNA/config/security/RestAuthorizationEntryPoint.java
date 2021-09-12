package DNA.config.security;

import DNA.bean.PublicResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description 当未登录或token失效时访问接口，自定义的返回结果
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/8/22 15:07
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        PublicResponse publicResponse = PublicResponse.error("尚未登录，请登录");
        publicResponse.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(publicResponse));
        out.flush();
        out.close();
    }
}
