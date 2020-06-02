package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.JwtAuthenticationException;
import kg.sabyrov.terrafit.models.JwtTokenRequest;
import kg.sabyrov.terrafit.models.JwtTokenResponse;
import kg.sabyrov.terrafit.service.AuthLogService;
import kg.sabyrov.terrafit.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
    private final AuthLogService authLogService;

    @Autowired
    public JwtAuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService jwtUserDetailsService, AuthLogService authLogService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.authLogService = authLogService;
    }

    @RequestMapping(value = "${jwt.get.token.uri}", method = RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody JwtTokenRequest jwtTokenRequest) throws JwtAuthenticationException {
        authenticate(jwtTokenRequest.getEmail(), jwtTokenRequest.getPassword());
        try {
            final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(jwtTokenRequest.getEmail());
            final String token = jwtUtil.generateToken(userDetails);

            authLogService.create(userDetails.getUsername(), Status.OK);
            return ResponseEntity.ok(new JwtTokenResponse(token));

        }catch (Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

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
