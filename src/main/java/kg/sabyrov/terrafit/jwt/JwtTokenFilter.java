package kg.sabyrov.terrafit.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import kg.sabyrov.terrafit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;


    @Value("${jwt.http.request.header}")
    private String tokenHeader;

    @Value("${jwt.get.token.uri}")
    private String loginPath;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = httpServletRequest.getHeader(this.tokenHeader);
        String email = null;
        String jwtToken = null;

        boolean isRequestNotNeedAuth = httpServletRequest.getRequestURI().equals("/user/register") || httpServletRequest.getRequestURI().equals(loginPath);
        if(requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") && !isRequestNotNeedAuth) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                email = jwtUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                httpServletRequest.setAttribute("expired", e.getMessage());
            } catch (MalformedJwtException e) {
                httpServletRequest.setAttribute("valueError", "Token error, unable to read JWT");
            }
        }
        else if(requestTokenHeader != null && !isRequestNotNeedAuth && !requestTokenHeader.startsWith("Bearer ")){
            httpServletRequest.setAttribute("bearerTokenAbsent", "JWT Token does not begin with Bearer String");
        }

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(email);

        if(userDetails == null) httpServletRequest.setAttribute("userInActive", "User is inActive");
//        if(!userDetails.isEnabled()) httpServletRequest.setAttribute("userInActive", "User is inactive");
        else if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            if(jwtUtil.validateToken(jwtToken, userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
