package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.personParamDto.PersonParamRequestDto;
import kg.sabyrov.terrafit.dto.personParamDto.PersonParamResponseDto;
import kg.sabyrov.terrafit.service.PersonParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/person-parameters")
@Api(tags = "Person Parameters", description = "Controller for write person parameters")
public class PersonParamController {


    @Autowired
    private PersonParamService personParamService;

    @ApiOperation(value = "FOR 'USER' - get all person-parameters")
    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(personParamService.findAllByUser(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'USER' - create person-parameters")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody PersonParamRequestDto personParamRequestDto){
        try {
            return new ResponseEntity<>(personParamService.create(personParamRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
