package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.dto.User.UserDto;

public interface UserService extends BaseService<User>{
    User findByEmailAndIsActive(String email, Integer isActive);

    User create(UserDto userDto);

    User findByEmail(String email);

    User findBySurnameAndName(String surname, String name);
}
