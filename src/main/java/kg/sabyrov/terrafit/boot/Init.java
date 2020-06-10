package kg.sabyrov.terrafit.boot;


import kg.sabyrov.terrafit.entity.*;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class Init implements CommandLineRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EmployeeRepository employeeRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final TrainingGroupCategoryRepository trainingGroupCategoryRepository;
    private final TrainingGroupRepository trainingGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final WalletRepository walletRepository;

    @Autowired
    public Init(UserRepository userRepository, RoleRepository roleRepository, EmployeeRepository employeeRepository, PromoCodeRepository promoCodeRepository, TrainingGroupCategoryRepository trainingGroupCategoryRepository, TrainingGroupRepository trainingGroupRepository, PasswordEncoder passwordEncoder, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.promoCodeRepository = promoCodeRepository;
        this.trainingGroupCategoryRepository = trainingGroupCategoryRepository;
        this.trainingGroupRepository = trainingGroupRepository;
        this.passwordEncoder = passwordEncoder;
        this.walletRepository = walletRepository;
    }

    @Override
    public void run(String... args) throws Exception {
//        //ROLES
//        Role roleAdmin = roleRepository.save(Role.builder()
//                .name("ROLE_ADMIN")
//                .build());
//
//        Role roleUser = roleRepository.save(Role.builder()
//                .name("ROLE_USER")
//                .build());
//
//
//        List<Role> roleList = new ArrayList<>();
//        roleList.add(roleAdmin);
//        roleList.add(roleUser);
//
//        List<Role> userList = new ArrayList<>();
//        userList.add(roleUser);
//
//        User admin = userRepository.save(User.builder()
//                .email("chef@gmail.com")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("MALE")
//                .birthDate(LocalDate.of(1999, 3, 1))
//                .name("Айдин")
//                .surname("Сабыров")
//                .isActive(1)
//                .roles(roleList)
//                .build());

//        User user = userRepository.save(User.builder()
//                .email("user@gmail.com")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("MALE")
//                .birthDate(LocalDate.of(1999,3, 1))
//                .name("Жома")
//                .surname("Акынов")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        Wallet walletUser = walletRepository.save(Wallet.builder()
//                .user(user)
//                .requisite("US123")
//                .status(Status.ACTIVE)
//                .balance(new BigDecimal(5000))
//                .build());
//
//        Wallet wallet = walletRepository.save( Wallet.builder()
//                .user(admin)
//                .requisite("AI123")
//                .status(Status.ACTIVE)
//                .balance(new BigDecimal(10000000))
//                .build());
//
//        User ka4ok = userRepository.save(User.builder()
//                .email("ka4ok@gmail.com")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-512-933")
//                .gender("MALE")
//                .birthDate(LocalDate.of(1995, 10, 2))
//                .name("Александр")
//                .surname("Михайлов")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        User  aigul = userRepository.save(User.builder()
//                .email("fitledi")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("FEMALE")
//                .birthDate(LocalDate.of(1997, 2, 14))
//                .name("Айгуль")
//                .surname("Баланчаева")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        User vlada = userRepository.save(User.builder()
//                .email("stepledi")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("FEMALE")
//                .birthDate(LocalDate.of(1997, 2, 14))
//                .name("Влада")
//                .surname("Баланчаева")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        User anton = userRepository.save(User.builder()
//                .email("anton")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("MALE")
//                .birthDate(LocalDate.of(1994, 2, 14))
//                .name("Антон")
//                .surname("Котенко")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        User asel = userRepository.save(User.builder()
//                .email("asel")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("FEMALE")
//                .birthDate(LocalDate.of(1997, 2, 14))
//                .name("Асель")
//                .surname("Баланчаева")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        User temirlan = userRepository.save(User.builder()
//                .email("temirlan")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("MALE")
//                .birthDate(LocalDate.of(1997, 2, 14))
//                .name("Темирлан")
//                .surname("Баланчаев")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        User karlygash = userRepository.save(User.builder()
//                .email("karlygash")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("FEMALE")
//                .birthDate(LocalDate.of(1997, 2, 14))
//                .name("Карлыгаш")
//                .surname("Баланчаева")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//        User kirill = userRepository.save(User.builder()
//                .email("kirill")
//                .password(passwordEncoder.encode("12345"))
//                .phoneNumber("+996(500)-511-932")
//                .gender("MALE")
//                .birthDate(LocalDate.of(2000, 2, 14))
//                .name("Кирилл")
//                .surname("Баланчаев")
//                .isActive(1)
//                .roles(userList)
//                .build());
//
//
//
//        //category
//
//        TrainingGroupCategory gym = trainingGroupCategoryRepository.save(TrainingGroupCategory.builder()
//                .name("Тренажерный зал")
//                .build());
//
//        TrainingGroupCategory fitnessGroups = trainingGroupCategoryRepository.save(TrainingGroupCategory.builder()
//                .name("Фитнесс-группы")
//                .build());
//
//        TrainingGroupCategory taekwondo = trainingGroupCategoryRepository.save(TrainingGroupCategory.builder()
//                .name("Таэквондо ITF")
//                .build());
//        //Employee
//
//        Employee gymCoach = employeeRepository.save(Employee.builder()
//                .position("Тренер тренажерного зала")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(ka4ok)
//                .build());
//
//        Employee fitnesledi = employeeRepository.save(Employee.builder()
//                .position("Фитнесс-тренер")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(aigul)
//                .build());
//
//        Employee stepledi = employeeRepository.save(Employee.builder()
//                .position("Фитнес-тренер")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(vlada)
//                .build());
//
//        Employee dancefit = employeeRepository.save(Employee.builder()
//                .position("Фитнес-тренер")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(asel)
//                .build());
//
//        Employee crossfit = employeeRepository.save(Employee.builder()
//                .position("Фитнес-тренер")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(anton)
//                .build());
//
//        Employee tkdTima = employeeRepository.save(Employee.builder()
//                .position("Тренер по таэквондо")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(temirlan)
//                .build());
//
//        Employee tkdKarl = employeeRepository.save(Employee.builder()
//                .position("Тренер по таэквондо")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(karlygash)
//                .build());
//
//        Employee tkdKirill = employeeRepository.save(Employee.builder()
//                .position("Тренер по таэквондо")
//                .salary(1000)
//                .status(Status.ACTIVE)
//                .user(kirill)
//                .build());
//
//        //training sections
//
//        TrainingSection gymDay = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(gym)
//                .name("Тренажерный зал(дневной)")
//                .subscriptionPrice(new BigDecimal(1500))
//                .trainingTime("08:00 - 16:00")
//                .employee(gymCoach)
//                .status(Status.ACTIVE)
//                .build());
//
//        TrainingSection gymEvening = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(gym)
//                .name("Тренажерный зал(вечерний)")
//                .subscriptionPrice(new BigDecimal(2000))
//                .trainingTime("08:00 - 23:00")
//                .employee(gymCoach)
//                .status(Status.ACTIVE)
//                .build());
//
//        TrainingSection fitledi = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(fitnessGroups)
//                .name("Фитнес-леди")
//                .subscriptionPrice(new BigDecimal(2000))
//                .trainingTime("ПН - СР - ПТ 19:00 - 20:00")
//                .employee(fitnesledi)
//                .status(Status.ACTIVE)
//                .build());
//
//        TrainingSection stepAerobika = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(fitnessGroups)
//                .name("Степ-аэробика")
//                .subscriptionPrice(new BigDecimal(2000))
//                .trainingTime("ВТ - ЧТ - СБ 19:00 - 20:00")
//                .employee(stepledi)
//                .status(Status.ACTIVE)
//                .build());
//
//        TrainingSection crossfitness = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(fitnessGroups)
//                .name("Кроссфит")
//                .subscriptionPrice(new BigDecimal(2000))
//                .trainingTime("ПН - СР - ПТ 21:00 - 22:00")
//                .employee(crossfit)
//                .status(Status.ACTIVE)
//                .build());
//
//        TrainingSection tkd1 = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(taekwondo)
//                .name("Таэквондо")
//                .subscriptionPrice(new BigDecimal(2000))
//                .trainingTime("ПН - СР - ПТ 20:00 - 21:30")
//                .employee(tkdTima)
//                .status(Status.ACTIVE)
//                .build());
//
//        TrainingSection tkd2 = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(taekwondo)
//                .name("Таэквондо")
//                .subscriptionPrice(new BigDecimal(2000))
//                .trainingTime("ПН - СР - ПТ 17:00 - 18:30")
//                .employee(tkdKirill)
//                .status(Status.ACTIVE)
//                .build());
//
//        TrainingSection tkd3 = trainingSectionRepository.save(TrainingSection.builder()
//                .trainingGroupCategory(taekwondo)
//                .name("Таэквондо")
//                .subscriptionPrice(new BigDecimal(2000))
//                .trainingTime("ПН - СР - ПТ 18:30 - 20:00")
//                .employee(tkdKarl)
//                .status(Status.ACTIVE)
//                .build());
//
//        //promo code
//
//        PromoCode promoCode1 = promoCodeRepository.save(PromoCode.builder()
//                .name("ЯКачок")
//                .discountPercentages(5)
//                .build());
//
//        PromoCode promoCode2 = promoCodeRepository.save(PromoCode.builder()
//                .name("ЯКия")
//                .discountPercentages(5)
//                .build());
//
//        PromoCode promoCode3 = promoCodeRepository.save(PromoCode.builder()
//                .name("Лето")
//                .discountPercentages(20)
//                .build());
//
//        PromoCode promoCode4 = promoCodeRepository.save(PromoCode.builder()
//                .name("Зима")
//                .discountPercentages(5)
//                .build());
//
//        PromoCode promoCode5 = promoCodeRepository.save(PromoCode.builder()
//                .name("Арнольд")
//                .discountPercentages(10)
//                .build());
//

    }
}
