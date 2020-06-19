package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.userDto.UserDto;
import kg.sabyrov.terrafit.dto.userDto.UserFindDto;
import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ConfirmationCodeModel;
import kg.sabyrov.terrafit.models.RecoveryModel;
import kg.sabyrov.terrafit.service.UserConfirmationCodeService;
import kg.sabyrov.terrafit.service.UserService;
import kg.sabyrov.terrafit.service.implementation.JavaMailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSenderService javaMailSenderService;

    @Autowired
    private UserConfirmationCodeService userConfirmationCodeService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>(userService.create(userDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/find")
    public ResponseEntity<?> search(@RequestBody UserFindDto userFindDto){
        try {
            return new ResponseEntity<>(userService.findBySurnameAndName(userFindDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//
//    @PostMapping("/find")
//    public ResponseEntity<?> search(@RequestBody String email){
//        try {
//            return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }


//    @GetMapping("/check")
//    public String check(){
//        return SecurityContextHolder.getContext().getAuthentication().getName();
////        return principal.getName();
//    }

    @PostMapping("/recovery")
    public ResponseEntity<?> recovery(@RequestBody RecoveryModel recoveryModel){
        try {
            return new ResponseEntity<>(javaMailSenderService.sendMessage(recoveryModel.getEmail()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody ConfirmationCodeModel confirmationCodeModel){
        try {
            return new ResponseEntity<>(userConfirmationCodeService.confirm(confirmationCodeModel), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
