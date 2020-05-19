package kg.sabyrov.terrafit.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        List<String> attributes = new ArrayList<>(Collections.list(httpServletRequest.getAttributeNames()));

        for (String s : attributes) {
            if(s.equals("expired")){
                httpServletResponse.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT, (String) httpServletRequest.getAttribute(s));
                break;
            }
            else if(s.equals("valueError")){
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, (String) httpServletRequest.getAttribute(s));
                break;
            }
            else if(s.equals("bearerTokenAbsent")){
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, (String) httpServletRequest.getAttribute(s));
                break;
            }
            else if(s.equals("userInActive")){
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, (String) httpServletRequest.getAttribute(s));
                break;
            }
            else httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, (String) httpServletRequest.getAttribute(s));
        }
    }
}
