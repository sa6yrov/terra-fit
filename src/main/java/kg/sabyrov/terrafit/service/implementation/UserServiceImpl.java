package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.userDto.UserFindDto;
import kg.sabyrov.terrafit.entity.Role;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.dto.userDto.UserDto;
import kg.sabyrov.terrafit.entity.Wallet;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private WalletService walletService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }


    @Override
    public User findByEmailAndIsActive(String email, Integer isActive) {
        return userRepository.findByEmailAndIsActive(email, isActive);
    }

    @Override
    public ResponseMessage create(UserDto userDto) throws UserRegisterException, UserNotFoundException {
        if(!checkUserModelForUnique(userDto.getEmail())) {
            throw  new UserRegisterException("User with this email already exists");
        }

        User user = saveAndGetUserByUserModel(userDto);
        createWalletForUser(user);

        return new ResponseMessage(user.getEmail() + " was successfully registered");

    }
    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        //        if(user == null) throw new UserNotFoundException("User with '" + email + "'  email not found");
        return userRepository.findByEmail(email);
    }
    @Override
    public List<User> findBySurnameAndName(UserFindDto userFindDto) throws UserNotFoundException {
        List<User> users = userRepository.findBySurnameAndName(userFindDto.getSurname(), userFindDto.getName());
        if(users == null) throw new UserNotFoundException("User with surname: '" + userFindDto.getSurname() + "' and name: '" + userFindDto.getName() + "' not found");
        return users;
    }

    @Override
    public User deActivateUser(String email) throws UserNotFoundException {
        User user = findByEmail(email);
        user.setIsActive(0);
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

    private User saveAndGetUserByUserModel(UserDto userDto){
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleUser);

        User user = User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .birthDate(userDto.getBirthDate())
                .gender(userDto.getGender())
                .isActive(1)
                .phoneNumber(userDto.getPhoneNumber())
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

    private boolean checkUserModelForUnique(String email) throws UserNotFoundException {
        return findByEmail(email) == null;
    }

}
