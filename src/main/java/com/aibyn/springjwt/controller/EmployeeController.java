package com.aibyn.springjwt.controller;

import com.aibyn.springjwt.dto.request.EmployeeDtoRequest;
import com.aibyn.springjwt.model.Employee;
import com.aibyn.springjwt.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/test/")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public String userAccess(){
        return "User Content";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String modAccess(){
        return "Moderator Content";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess(){
        return "Admin Content";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Employee> getById(@PathVariable(value = "id") Long id){
        Employee employee = this.employeeService.getByIdThrowException(id);
        return ResponseEntity.ok().body(employee);
    }

    @GetMapping("/employee")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<Employee> getAll(){
        return this.employeeService.getAll();
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> create(@Valid @RequestBody EmployeeDtoRequest employeeDtoRequest){
        this.employeeService.create(employeeDtoRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> update(@RequestBody EmployeeDtoRequest employeeDtoRequest,
                                             @PathVariable(name = "id") Long id){
        this.employeeService.update(employeeDtoRequest, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id){
        this.employeeService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
