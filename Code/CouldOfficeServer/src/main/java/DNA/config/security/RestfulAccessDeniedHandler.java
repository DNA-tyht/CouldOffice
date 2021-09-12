package DNA.config.security;

import DNA.bean.PublicResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/8/22 15:12
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                       AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter out = httpServletResponse.getWriter();
        PublicResponse publicResponse = PublicResponse.error("权限不足，请联系管理员");
        publicResponse.setCode(403);
        out.write(new ObjectMapper().writeValueAsString(publicResponse));
        out.flush();
        out.close();
    }
}
