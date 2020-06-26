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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserService userService;

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
        return mapEmployeeToModel(save(Employee.builder()
                .user(getUserFromDb(employeeRequestDto.getEmail()))
                .position(employeeRequestDto.getPosition())
                .status(Status.ACTIVE)
                .build()));
    }

    @Override
    public List<EmployeeResponseDto> findAllModels() {
        List<Employee> employees = getAll();
        List<EmployeeResponseDto> employeeResponseDtos = new ArrayList<>();

        for (Employee e : employees) {
            employeeResponseDtos.add(mapEmployeeToModel(e));
        }
        return employeeResponseDtos;
    }

    private EmployeeResponseDto mapEmployeeToModel(Employee employee){
        return EmployeeResponseDto.builder()
                .name(employee.getUser().getName())
                .surname(employee.getUser().getSurname())
                .email(employee.getUser().getEmail())
                .birthDate(employee.getUser().getBirthDate())
                .phoneNumber(employee.getUser().getPhoneNumber())
                .gender(employee.getUser().getGender())
                .position(employee.getPosition())
                .build();
    }
    private User getUserFromDb(String email) throws UserNotFoundException {
        User user = userService.findByEmail(email);
        if(user == null) throw new UserNotFoundException("User with this email not found");
        return user;
    }
}
