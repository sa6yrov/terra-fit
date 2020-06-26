package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.userDto.UserFindDto;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Api(tags = "'User' queries", value = "UserQueries", description = "Controller for User queries")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "FOR 'ADMIN' - get all users")
    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - find user by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        try {
            return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - find user by 'name' and 'surname'")
    @PostMapping("/find/byName")
    public ResponseEntity<?> search(@RequestBody UserFindDto userFindDto){
        try {
            return new ResponseEntity<>(userService.findBySurnameAndName(userFindDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/find/byEmail")
    public ResponseEntity<?> search(@RequestBody String email){
        try {
            return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
