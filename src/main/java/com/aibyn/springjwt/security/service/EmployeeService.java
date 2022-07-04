package com.aibyn.springjwt.security.service;

import com.aibyn.springjwt.dto.request.EmployeeDtoRequest;
import com.aibyn.springjwt.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> getById(Long id);

    Employee getByIdThrowException(Long id);

    List<Employee> getAll();

    void create(EmployeeDtoRequest employeeDtoRequest);

    void update(EmployeeDtoRequest employeeDtoRequest, Long id);

    void delete(Long id);
}
