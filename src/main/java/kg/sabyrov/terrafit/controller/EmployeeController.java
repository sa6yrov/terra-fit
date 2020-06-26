package kg.sabyrov.terrafit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kg.sabyrov.terrafit.dto.employeeDto.EmployeeRequestDto;
import kg.sabyrov.terrafit.entity.Employee;
import kg.sabyrov.terrafit.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/employee")
@Api(tags = "'Employee' queries", value = "EmployeeQueries", description = "Controller for Employee queries")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @ApiOperation(value = "PERMIT ALL - get all EmployeeModels")
    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity<>(employeeService.findAllModels(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - create Employee")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmployeeRequestDto employeeRequestDto){
        try {
            return new ResponseEntity<>(employeeService.create(employeeRequestDto), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "FOR 'ADMIN' - get employee by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return new ResponseEntity<Employee>(employeeService.getById(id), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        }
}
