package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.models.UserModel;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping("/register")
    public User user (@RequestBody UserModel userModel){
        return userService.create(userModel);
    }

    @GetMapping("/check")
    public String check(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
//        return principal.getName();
    }

}
