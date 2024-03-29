package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.personParamDto.PersonParamRequestDto;
import kg.sabyrov.terrafit.dto.personParamDto.PersonParamResponseDto;
import kg.sabyrov.terrafit.entity.PersonParam;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.repository.PersonParamRepository;
import kg.sabyrov.terrafit.service.PersonParamService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public List<PersonParamResponseDto> findAllByUser(String email) throws UserNotFoundException {
        List<PersonParamResponseDto> personParamResponseDtos = new ArrayList<>();
        User user = userService.findByEmail(email);
        List<PersonParam> personParams = personParamRepository.findAllByUser(user);

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

    @Override
    public PersonParamResponseDto create(PersonParamRequestDto personParamRequestDto) throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User user = userService.findByEmail(email);
        PersonParam personParam = save(PersonParam.builder()
                .user(user)
                .height(personParamRequestDto.getHeight())
                .weight(personParamRequestDto.getWeight())
                .shoulderWidth(personParamRequestDto.getShoulderWidth())
                .hipGirth(personParamRequestDto.getHipGirth())
                .bicepGirth(personParamRequestDto.getBicepGirth())
                .chestGirth(personParamRequestDto.getChestGirth())
                .neckGirth(personParamRequestDto.getNeckGirth())
                .noteGirth(personParamRequestDto.getNoteGirth())
                .waistGirth(personParamRequestDto.getWaistGirth())
                .build());

        return getPersonParamResponseObject(personParam);
    }

    private PersonParamResponseDto getPersonParamResponseObject(PersonParam personParam){
        return PersonParamResponseDto.builder()
                .height(personParam.getHeight())
                .weight(personParam.getWeight())
                .shoulderWidth(personParam.getShoulderWidth())
                .noteGirth(personParam.getNoteGirth())
                .neckGirth(personParam.getNeckGirth())
                .hipGirth(personParam.getHipGirth())
                .chestGirth(personParam.getChestGirth())
                .bicepGirth(personParam.getBicepGirth())
                .waistGirth(personParam.getWaistGirth())
                .createdDate(personParam.getCreatedDate())
                .build();
    }
}
