package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.personParamDto.PersonParamResponseDto;
import kg.sabyrov.terrafit.entity.PersonParam;
import kg.sabyrov.terrafit.repository.PersonParamRepository;
import kg.sabyrov.terrafit.service.PersonParamService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonParamServiceImpl implements PersonParamService {
    private final PersonParamRepository personParamRepository;
    private final UserService userService;

    @Autowired
    public PersonParamServiceImpl(PersonParamRepository personParamRepository, UserService userService) {
        this.personParamRepository = personParamRepository;
        this.userService = userService;
    }

    @Override
    public PersonParam save(PersonParam personParam) {
        return personParamRepository.save(personParam);
    }

    @Override
    public PersonParam getById(Long id) {
        Optional<PersonParam> personParamOptional = personParamRepository.findById(id);
        return personParamOptional.orElse(null);
    }

    @Override
    public List<PersonParam> getAll() {
        return personParamRepository.findAll();
    }

    @Override
    public List<PersonParamResponseDto> findAll() {
        List<PersonParamResponseDto> personParamResponseDtos = new ArrayList<>();
        List<PersonParam> personParams = getAll();

        for (PersonParam p : personParams) {
            personParamResponseDtos.add(PersonParamResponseDto.builder()
                    .height(p.getHeight())
                    .weight(p.getWeight())
                    .waistGirth(p.getWaistGirth())
                    .bicepGirth(p.getBicepGirth())
                    .chestGirth(p.getChestGirth())
                    .hipGirth(p.getHipGirth())
                    .neckGirth(p.getNeckGirth())
                    .noteGirth(p.getNoteGirth())
                    .shoulderWidth(p.getShoulderWidth())
                    .createdDate(p.getCreatedDate())
                    .build());
        }
        return personParamResponseDtos;
    }
}
