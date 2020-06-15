package kg.sabyrov.terrafit.service;


import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ConfirmationCodeModel;

public interface UserConfirmationCodeService extends BaseService<UserConfirmationCode>{
    UserConfirmationCode create(String email) throws UserNotFoundException;

    UserConfirmationCode findConfirmationCodeByUser(User user) throws UserNotFoundException;

    String confirm(ConfirmationCodeModel confirmationCodeModel) throws UserNotFoundException;
}
