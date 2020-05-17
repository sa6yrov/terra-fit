package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.entity.User;

public interface UserService extends BaseService<User>{
    User findByEmail(String email);

    User registre(User user);
}
