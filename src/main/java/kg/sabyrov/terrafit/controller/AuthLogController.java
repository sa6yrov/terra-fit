package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.service.AuthLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("auth-log")
public class AuthLogController {

    @Autowired
    private AuthLogService authLogService;

    @GetMapping
    public List<AuthLog> getAll(){
        return authLogService.getAll();
    }


}
