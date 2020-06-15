package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ConfirmationCodeModel;
import kg.sabyrov.terrafit.repository.UserConfirmationCodeRepository;
import kg.sabyrov.terrafit.service.UserConfirmationCodeService;
import kg.sabyrov.terrafit.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserConfirmationCodeServiceImpl implements UserConfirmationCodeService {
    @Autowired
    private UserConfirmationCodeRepository userConfirmationCodeRepository;

    @Autowired
    private UserService userService;

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

    @Override
    public UserConfirmationCode create(String email) throws UserNotFoundException {
        User user = getUserFromDb(email);
        return save(UserConfirmationCode.builder()
                .code(generateConfirmationCode())
                .user(user)
                .isActive(true)
                .build());
    }

    @Override
    public UserConfirmationCode findConfirmationCodeByUser(User user) throws UserNotFoundException {
         return userConfirmationCodeRepository.findByUserAndActive(user.getId(), true);

    }

    @Override
    public String confirm(ConfirmationCodeModel confirmationCodeModel) throws UserNotFoundException {
        User user = getUserFromDb(confirmationCodeModel.getEmail());
        UserConfirmationCode userConfirmationCode = findConfirmationCodeByUser(user);
        if(userConfirmationCode.getCode().equals(confirmationCodeModel.getCode())){
            userService.activation(confirmationCodeModel.getEmail());
            userConfirmationCode.setActive(false);
            save(userConfirmationCode);
        }
        return "Your account was recovered";
    }

    private String generateConfirmationCode(){
        return RandomStringUtils.random(4, true, true);
    }

    private User getUserFromDb(String email) throws UserNotFoundException {
        User user = userService.findByEmail(email);
        if(user == null) throw new UserNotFoundException("user with '" + email + "' email not found");
        return user;
    }
}
