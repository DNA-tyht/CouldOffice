package DNA.config.filter;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Description
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/9/2 15:03
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        for (ConfigAttribute configAttribute: configAttributes) {
            //获取当前url角色
            String needRole = configAttribute.getAttribute();
            //判断角色是否是登录即可访问的角色，此角色在CustomFilter中设置
            if ("ROLE_LOGIN".equals(needRole)) {
                //判断是否登录
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new AccessDeniedException("尚未登录，请登录");
                } else {
                    return;
                }
            }
            //判断用户角色是否为url需要的角色
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority: authorities) {
                if (authority.getAuthority().equals(needRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
