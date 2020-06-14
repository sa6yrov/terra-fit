package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import kg.sabyrov.terrafit.repository.UserConfirmationCodeRepository;
import kg.sabyrov.terrafit.service.UserConfirmationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserConfirmationCodeServiceImpl implements UserConfirmationCodeService {
    @Autowired
    private UserConfirmationCodeRepository userConfirmationCodeRepository;

    @Override
    public UserConfirmationCode save(UserConfirmationCode userConfirmationCode) {
        return userConfirmationCodeRepository.save(userConfirmationCode);
    }

    @Override
    public UserConfirmationCode getById(Long id){
        Optional<UserConfirmationCode> userConfirmationCodeOptional = userConfirmationCodeRepository.findById(id);
        return userConfirmationCodeOptional.orElse(null);
    }

    @Override
    public List<UserConfirmationCode> getAll() {
        return userConfirmationCodeRepository.findAll();
    }
}
