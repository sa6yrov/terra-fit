package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.userDto.UserModel;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UserModel userModel){
        try {
            return new ResponseEntity<>(userService.create(userModel), HttpStatus.OK);
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
    public ResponseEntity<?> search(String surname, String name){
        try {
            return new ResponseEntity<>(userService.findBySurnameAndName(surname, name), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

//    @PostMapping("/find")
//    public ResponseEntity<?> search(String code){
//
//    }

    @GetMapping("/check")
    public String check(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
//        return principal.getName();
    }

}
