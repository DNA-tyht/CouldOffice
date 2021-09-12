package DNA.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author 脱氧核糖
 * @Version 1.0
 * @Date 2021/8/21 21:41
 */
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
    * @Description 根据用户信息生成token
    * @Return [userDetails]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:13
    */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
    * @Description 根据负载生成JWT,TOKEN
    * @Return [claims]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:13
    */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
    * @Description 从token中获取username
    * @Return [token]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:15
    */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
    * @Description 生成token失效时间
    * @Return []
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:15
    */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
    * @Description 从token里面获取荷载
    * @Return [token]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:15
    */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws( token.replaceFirst("\ufeff", ""))
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
    * @Description 验证token是否有效
    * @Return [token, userDetails]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:16
    */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
    * @Description 判断token是否可以被刷新
    * @Return [token]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:16
    */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
    * @Description 刷新token
    * @Return [token]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:16
    */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
    * @Description 判定token是否失效
    * @Return [token]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:16
    */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        return expireDate.before(new Date());
    }

    /**
    * @Description 从token中获取过期时间
    * @Return [token]
    * @Author 脱氧核糖
    * @Date 2021/8/22 13:16
    */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
}