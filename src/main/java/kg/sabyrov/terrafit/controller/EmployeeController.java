package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.employeeDto.EmployeeRequestDto;
import kg.sabyrov.terrafit.dto.employeeDto.EmployeeResponseDto;
import kg.sabyrov.terrafit.entity.Employee;
import kg.sabyrov.terrafit.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(employeeService.findAllModels(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(EmployeeRequestDto employeeRequestDto){
        try {
            return new ResponseEntity<>(employeeService.create(employeeRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return new ResponseEntity<Employee>(employeeService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
}
