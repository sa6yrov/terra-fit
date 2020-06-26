package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.authLogDto.RequestTwoTimeAndUserEmail;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.service.AuthLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth-log")
@Api(tags = "'Auth-Log' queries", value = "AuthLogQueries", description = "Controller for Auth-Log queries")
public class AuthLogController {

    @Autowired
    private AuthLogService authLogService;

    @ApiOperation(value = "FOR 'ADMIN' get all authLog models")
    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(authLogService.getAllModels(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get all authLog models by user in time intervals")
    @PostMapping("/user")
    public ResponseEntity<?>  getAllByUser(@RequestBody RequestTwoTimeAndUserEmail requestTwoTimeAndUserEmail){
        try {
            return new ResponseEntity<>(authLogService.getAllByCreatedDateBetweenAndUser(requestTwoTimeAndUserEmail), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' get all authLog models int time intervals")
    @PostMapping
    public ResponseEntity<?> getAllModelsByTime(@RequestBody RequestTwoLocalDateTimeDto twoLocalDateTimeDto){
        try {
            return new ResponseEntity<>(authLogService.getAllByCreatedDateBetween(twoLocalDateTimeDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
