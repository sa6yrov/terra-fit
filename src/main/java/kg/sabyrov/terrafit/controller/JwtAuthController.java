package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.exceptions.JwtAuthenticationException;
import kg.sabyrov.terrafit.models.JwtTokenRequest;
import kg.sabyrov.terrafit.models.JwtTokenResponse;
import kg.sabyrov.terrafit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtAuthController {

    @Value("${jwt.http.request.header}")
    private String tokenHeader;


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService jwtUserDetailsService;

    @Autowired
    public JwtAuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService jwtUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody JwtTokenRequest jwtTokenRequest) throws JwtAuthenticationException {
        authenticate(jwtTokenRequest.getEmail(), jwtTokenRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtTokenRequest.getEmail());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtTokenResponse(token));
    }

    private void authenticate(String email, String password) throws JwtAuthenticationException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (DisabledException e){
            throw new JwtAuthenticationException("USER_DISABLED");
        }catch (BadCredentialsException e){
            throw new JwtAuthenticationException("INVALID_CREDENTIALS");
        }
    }
}
