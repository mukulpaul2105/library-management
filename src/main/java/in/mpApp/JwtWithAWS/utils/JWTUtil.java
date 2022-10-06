package in.mpApp.JwtWithAWS.utils;

import in.mpApp.JwtWithAWS.dtos.TokenDTO;
import in.mpApp.JwtWithAWS.dtos.UserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
This class contain 2 method
1. Generate Token based on the User details
2. Validating the token
 */
@Component
@Slf4j
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiry}")
    private Long expiry;

    public TokenDTO generateToken(final UserDTO userDTO) {
        // Creating Custom Claims
        final Map<String, Object> claims = new HashMap<>();
        claims.put("account", userDTO.getId());
        final LocalDateTime currentLTD = LocalDateTime.now();
        log.info("Current Local Date Time: {} ", currentLTD);
        final LocalDateTime expiryLTD = currentLTD.plus(expiry, ChronoUnit.MINUTES);
        log.info("Expiry Local Date Time: ", expiryLTD);
        final Date expiryDate = Date.from(expiryLTD.atZone(ZoneId.systemDefault()).toInstant());
        log.info("Expiry Time in Date form: ", expiryDate);
        // Setting Secret Key
        final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        // Generating Token
        final String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(userDTO.getUsername())
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
        log.info("Generated Token: {} ", token);
        return new TokenDTO(token, expiryLTD);
    }

    public Boolean validateToken(final String token, final UserDTO userDTO) {
        log.info("Validating Token for User Details: {} with Token: {} ", token, " ---- ", userDTO);
        final Claims claims = getAllClaims(token);
        final String username = claims.getSubject();
        final Date expiryDate = claims.getExpiration();
        final Date currentDate = new Date();

        if (StringUtils.hasText(username) && username.equals(userDTO.getUsername()) && expiryDate.after(currentDate)){
            return true;
        } else {
            return false;
        }
    }

    public String getUsername(final String token) {
        return getAllClaims(token).getSubject();
    }

    public Long getUserId(final String token) {
        return getAllClaims(token).get("account", Long.class);
    }

    private Claims getAllClaims(final String token) {
        final SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        final JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        return claimsJws.getBody();
    }

}
