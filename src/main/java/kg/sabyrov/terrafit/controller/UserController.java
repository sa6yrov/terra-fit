package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.userDto.UserDto;
import kg.sabyrov.terrafit.dto.userDto.UserFindDto;
import kg.sabyrov.terrafit.service.UserService;
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
    private JavaMailSender javaMailSender;

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


    @GetMapping("/check")
    public String check(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
//        return principal.getName();
    }

    @PostMapping("/recovery")
    public ResponseEntity<?> recovery(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recovering your account");
        message.setText();
    }

}
