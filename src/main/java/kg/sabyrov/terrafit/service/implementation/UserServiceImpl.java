package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Role;
import kg.sabyrov.terrafit.entity.User;
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
    public User registre(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");

        List<Role> roleList = new ArrayList<>();
        roleList.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setIs_active(1);
        user.setRoles(roleList);

        return save(user);
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

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
