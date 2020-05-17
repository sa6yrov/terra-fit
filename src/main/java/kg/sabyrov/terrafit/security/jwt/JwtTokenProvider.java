package kg.sabyrov.terrafit.security.jwt;

import io.jsonwebtoken.*;
import kg.sabyrov.terrafit.entity.Role;
import kg.sabyrov.terrafit.exceptions.JwtAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expiration.in.seconds}")
    private Long validityInSeconds;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String email, List<Role> roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", getRoleNames(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInSeconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token){
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    public boolean validateToken(String token) throws JwtAuthenticationException {
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if(claimsJws.getBody().getExpiration().before(new Date())){
                return false;
            }
            return true;
        }catch (JwtException | IllegalArgumentException e){
            throw new JwtAuthenticationException("Jwt token is expired or invalid");
        }
    }

    public List<String> getRoleNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getName());
        });
        return result;
    }
}
