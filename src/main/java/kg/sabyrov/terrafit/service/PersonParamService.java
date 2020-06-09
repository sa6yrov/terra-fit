package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.personParamDto.PersonParamResponseDto;
import kg.sabyrov.terrafit.entity.PersonParam;

import java.util.List;

public interface PersonParamService extends BaseService<PersonParam> {
    List<PersonParamResponseDto> findAll();
}
