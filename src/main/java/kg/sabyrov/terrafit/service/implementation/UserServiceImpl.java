package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Role;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.models.UserModel;
import kg.sabyrov.terrafit.repository.RoleRepository;
import kg.sabyrov.terrafit.repository.UserRepository;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public User findByEmailAndIsActive(String email, Integer isActive) {
        return userRepository.findByEmailAndIsActive(email, isActive);
    }

    @Override
    public User create(UserModel userModel) {
        Role roleUser = roleRepository.findByName("ROLE_USER");

        List<Role> roleList = new ArrayList<>();
        roleList.add(roleUser);

        User user = User.builder()
                .email(userModel.getEmail())
                .name(userModel.getName())
                .surname(userModel.getSurname())
                .password(passwordEncoder.encode(userModel.getPassword()))
                .birthDate(userModel.getBirthDate())
                .gender(userModel.getGender())
                .isActive(1)
                .phoneNumber(userModel.getPhoneNumber())
                .roles(roleList)
                .build();


        return save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }


}
