package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.personParamDto.PersonParamResponseDto;
import kg.sabyrov.terrafit.service.PersonParamService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/person-parameters")
public class PersonParamController {
    private final PersonParamService personParamService;

    public PersonParamController(PersonParamService personParamService) {
        this.personParamService = personParamService;
    }

    @GetMapping
    public List<PersonParamResponseDto> getAll(){

    }
}
