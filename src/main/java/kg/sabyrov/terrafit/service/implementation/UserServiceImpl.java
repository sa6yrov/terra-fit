package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.userDto.UserFindDto;
import kg.sabyrov.terrafit.dto.userDto.UserResponseDto;
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
import java.util.stream.Collectors;

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

    @Autowired
    private JavaMailSenderService javaMailSenderService;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmailAndIsActive(String email, Integer isActive) {
        return userRepository.findByEmailAndIsActive(email, isActive);
    }

    @Override
    public ResponseMessage create(UserDto userDto) throws Exception {
        if(!checkUserModelForUnique(userDto.getEmail())) {
            throw  new UserRegisterException("User with this email already exists");
        }

        User user = saveAndGetUserByUserModel(userDto);
        createWalletForUser(user);
        javaMailSenderService.sendMessage(user.getEmail()); //java mail sender for send code to user's email
        return new ResponseMessage(user.getEmail() + ", please check your email and confirm code");

    }
    @Override
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    @Override
    public List<UserResponseDto> findBySurnameAndName(UserFindDto userFindDto) throws UserNotFoundException {
        List<UserResponseDto> users = userRepository.findBySurnameAndName(
                userFindDto.getSurname(),
                userFindDto.getName()).stream().map(this::mapUserToModel).collect(Collectors.toList());

        if(users.isEmpty()) throw new UserNotFoundException("Users with surname: '" + userFindDto.getSurname() + "' and name: '" + userFindDto.getName() + "' not found");
        return users;
    }

    @Override
    public UserResponseDto getModelByEmail(String email) {
        return mapUserToModel(findByEmail(email));
    }

    @Override
    public User deActivateUser(String email){
        User user = findByEmail(email);
        user.setIsActive(0);
        return save(user);
    }

    @Override
    public User activation(String email) {
         User user = findByEmail(email);
         user.setIsActive(1);
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

        return save(User.builder()
                .email(userDto.getEmail())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .birthDate(userDto.getBirthDate())
                .gender(userDto.getGender())
                .isActive(0)
                .phoneNumber(userDto.getPhoneNumber())
                .roles(roleList)
                .build());
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

    private UserResponseDto mapUserToModel(User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .build();
    }
}
