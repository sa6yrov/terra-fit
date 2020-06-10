package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.personParamDto.PersonParamRequestDto;
import kg.sabyrov.terrafit.dto.personParamDto.PersonParamResponseDto;
import kg.sabyrov.terrafit.service.PersonParamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/person-parameters")
public class PersonParamController {
    private final PersonParamService personParamService;

    public PersonParamController(PersonParamService personParamService) {
        this.personParamService = personParamService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(Principal principal){
        try {
            return new ResponseEntity<>(personParamService.findAllByUser(principal.getName()), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(PersonParamRequestDto personParamRequestDto){
        try {
            return new ResponseEntity<>(personParamService.create(personParamRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
