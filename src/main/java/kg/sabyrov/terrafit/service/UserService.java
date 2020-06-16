package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.userDto.UserFindDto;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.dto.userDto.UserDto;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserRegisterException;
import kg.sabyrov.terrafit.models.ResponseMessage;

import java.util.List;

public interface UserService extends BaseService<User>{
    User findByEmailAndIsActive(String email, Integer isActive);

    ResponseMessage create(UserDto userDto) throws Exception;

    User findByEmail(String email);

    List<User> findBySurnameAndName(UserFindDto userFindDto) throws UserNotFoundException;

    User deActivateUser(String email);

    User activation(String email);
}
