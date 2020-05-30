package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Role;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.dto.userDto.UserModel;
import kg.sabyrov.terrafit.entity.Wallet;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.UserRegisterException;
import kg.sabyrov.terrafit.models.ResponseMessage;
import kg.sabyrov.terrafit.repository.RoleRepository;
import kg.sabyrov.terrafit.repository.UserRepository;
import kg.sabyrov.terrafit.service.UserService;
import kg.sabyrov.terrafit.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private  PasswordEncoder passwordEncoder;
    private WalletService walletService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, WalletService walletService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletService = walletService;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public User findByEmailAndIsActive(String email, Integer isActive) {
        return userRepository.findByEmailAndIsActive(email, isActive);
    }

    @Override
    public ResponseMessage create(UserModel userModel) throws UserRegisterException {
        if(!checkUserModelForUnique(userModel.getEmail())) {
            throw  new UserRegisterException("User with this email already exists");
        }

        User user = saveAndGetUserByUserModel(userModel);
        createWalletForUser(user);

        return new ResponseMessage(user.getEmail() + " was successfully registered");

    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Override
    public User findBySurnameAndName(String surname, String name) {
        return userRepository.findBySurnameAndName(surname, name);
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
    private User saveAndGetUserByUserModel(UserModel userModel){
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

    private void createWalletForUser(User user){
        Wallet wallet = Wallet.builder()
                .balance(new BigDecimal(BigInteger.ZERO))
                .status(Status.ACTIVE)
                .user(user)
                .requisite(walletService.generateRequisite())
                .build();

        walletService.save(wallet);
    }

    private boolean checkUserModelForUnique(String email){
        return findByEmail(email) == null;
    }

}
