package kg.sabyrov.terrafit.jwt;

import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.JwtAuthenticationException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.service.AuthLogService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthLogService authLogService;

    @Autowired
    private UserService userService;

    public void authenticate(String email, String password) throws JwtAuthenticationException {
        try {
            if(authLogService.countAllByStatusAndUser(Status.FAILED, email) >= 3){
                userService.deActivateUser(email);
            }
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            authLogService.create(email, Status.OK);
        }catch (DisabledException e){
            authLogService.create(email, Status.FAILED);
            throw new JwtAuthenticationException("USER_DISABLED");
        }catch (BadCredentialsException e){
            authLogService.create(email, Status.FAILED);
            throw new JwtAuthenticationException("INVALID_CREDENTIALS");
        }
    }
}
