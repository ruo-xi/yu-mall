package top.yu.mall.kit;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import top.yu.mall.config.properties.JwtProperties;
import top.yu.mall.domain.UmsMember;
import top.yu.mall.exception.BusnessException;

import java.util.Date;
import java.util.HashMap;

/**
 * @Author: yu
 * @Date: 4/13/21:3:28 PM
 */
public class JwtKit {
    @Autowired
    JwtProperties jwtProperties;

    public String createToken(UmsMember umsMember) {

        HashMap<String, Object> claims = new HashMap<>();
        claims.put("username", umsMember.getUsername());
        claims.put("id", umsMember.getId());
        claims.put("memberLevelId", umsMember.getMemberLevelId());

        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpire()))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .compact();
    }

    public Claims parseJwtToken(String jwtToken) throws BusnessException {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new BusnessException("token 已过期");
        } catch (UnsupportedJwtException | IllegalArgumentException | SignatureException | MalformedJwtException e) {
            throw new BusnessException("token 错误");
        }

        return claims;
    }
}
