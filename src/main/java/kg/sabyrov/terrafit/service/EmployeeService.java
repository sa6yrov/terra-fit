package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.employeeDto.EmployeeRequestDto;
import kg.sabyrov.terrafit.dto.employeeDto.EmployeeResponseDto;
import kg.sabyrov.terrafit.entity.Employee;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;

import java.util.List;

public interface EmployeeService extends BaseService<Employee> {
    Employee findByUserEmail(String email);
    EmployeeResponseDto create(EmployeeRequestDto employeeRequestDto) throws UserNotFoundException;
    List<EmployeeResponseDto> findAllModels();
}
