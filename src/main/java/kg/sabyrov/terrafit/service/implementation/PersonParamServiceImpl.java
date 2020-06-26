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
    @Autowired
    private PersonParamRepository personParamRepository;
    @Autowired
    private UserService userService;

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
    public List<PersonParamResponseDto> findAllByUser() throws UserNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();

        List<PersonParamResponseDto> personParamResponseDtos = new ArrayList<>();
        User user = userService.findByEmail(email);
        List<PersonParam> personParams = personParamRepository.findAllByUser(user);

        for (PersonParam p : personParams) {
            personParamResponseDtos.add(getPersonParamResponseObject(p));
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
                .waistGirth(personParamRequestDto.getWaistGirth())
                .build());

        return getPersonParamResponseObject(personParam);
    }

    private PersonParamResponseDto getPersonParamResponseObject(PersonParam personParam){
        return PersonParamResponseDto.builder()
                .height(personParam.getHeight())
                .weight(personParam.getWeight())
                .shoulderWidth(personParam.getShoulderWidth())
                .neckGirth(personParam.getNeckGirth())
                .hipGirth(personParam.getHipGirth())
                .chestGirth(personParam.getChestGirth())
                .bicepGirth(personParam.getBicepGirth())
                .waistGirth(personParam.getWaistGirth())
                .createdDate(personParam.getCreatedDate())
                .build();
    }
}
