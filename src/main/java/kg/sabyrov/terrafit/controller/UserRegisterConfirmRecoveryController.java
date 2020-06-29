package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.userDto.UserDto;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ConfirmationCodeModel;
import kg.sabyrov.terrafit.models.RecoveryModel;
import kg.sabyrov.terrafit.service.UserConfirmationCodeService;
import kg.sabyrov.terrafit.service.UserService;
import kg.sabyrov.terrafit.service.implementation.JavaMailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "'Register' , 'Confirm', 'Recovery' queries", description = "Controller for register, confirm, recovery")
public class UserRegisterConfirmRecoveryController {
    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSenderService javaMailSenderService;

    @Autowired
    private UserConfirmationCodeService userConfirmationCodeService;

    @ApiOperation(value = "FOR ANYBODY - register")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register (@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>(userService.create(userDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'USER' - recovery")
    @RequestMapping(value = "/recovery", method = RequestMethod.POST)
    public ResponseEntity<?> recovery(@RequestBody RecoveryModel recoveryModel){
        try {
            return new ResponseEntity<>(javaMailSenderService.sendMessage(recoveryModel.getEmail()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'USER' - confirm code")
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public ResponseEntity<?> confirm(@RequestBody ConfirmationCodeModel confirmationCodeModel){
        try {
            return new ResponseEntity<>(userConfirmationCodeService.confirm(confirmationCodeModel), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
