package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.employeeDto.EmployeeRequestDto;
import kg.sabyrov.terrafit.dto.employeeDto.EmployeeResponseDto;
import kg.sabyrov.terrafit.entity.Employee;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.repository.EmployeeRepository;
import kg.sabyrov.terrafit.service.EmployeeService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserService userService;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.userService = userService;
    }

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee getById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        return employeeOptional.orElse(null);
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByUserEmail(String email) {
        return employeeRepository.findByUser_Email(email);
    }

    @Override
    public EmployeeResponseDto create(EmployeeRequestDto employeeRequestDto) throws UserNotFoundException {
        Employee employee = save(Employee.builder()
                .user(getUserFromDb(employeeRequestDto.getEmail()))
                .salary(0)
                .position(employeeRequestDto.getPosition())
                .status(Status.ACTIVE)
                .build());

        return EmployeeResponseDto.builder()
                .name(employee.getUser().getName())
                .surname(employee.getUser().getSurname())
                .email(employee.getUser().getEmail())
                .birthDate(employee.getUser().getBirthDate())
                .phoneNumber(employee.getUser().getPhoneNumber())
                .gender(employee.getUser().getGender())
                .build();
    }

    private User getUserFromDb(String email) throws UserNotFoundException {
        User user = userService.findByEmail(email);
        if(user == null) throw  new UserNotFoundException("User with this email not found");
        return user;

    }
}
