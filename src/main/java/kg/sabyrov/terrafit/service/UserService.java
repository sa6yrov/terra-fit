package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.models.UserModel;

public interface UserService extends BaseService<User>{
    User findByEmailAndIsActive(String email, Integer isActive);

    User create(UserModel userModel);

    User findByEmail(String email);
}
