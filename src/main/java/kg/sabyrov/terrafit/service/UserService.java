package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.dto.userDto.UserModel;
import kg.sabyrov.terrafit.exceptions.UserRegisterException;
import kg.sabyrov.terrafit.models.ResponseMessage;

public interface UserService extends BaseService<User>{
    User findByEmailAndIsActive(String email, Integer isActive);

    ResponseMessage create(UserModel userModel) throws UserRegisterException;

    User findByEmail(String email);

    User findBySurnameAndName(String surname, String name);
}
