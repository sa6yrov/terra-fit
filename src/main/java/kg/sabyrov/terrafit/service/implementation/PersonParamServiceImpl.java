package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.PersonParam;
import kg.sabyrov.terrafit.repository.PersonParamRepository;
import kg.sabyrov.terrafit.service.PersonParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonParamServiceImpl implements PersonParamService {
    private final PersonParamRepository personParamRepository;

    @Autowired
    public PersonParamServiceImpl(PersonParamRepository personParamRepository) {
        this.personParamRepository = personParamRepository;
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
}
