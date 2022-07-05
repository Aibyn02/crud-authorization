package com.aibyn.springjwt.service;

import com.aibyn.springjwt.dto.request.EmployeeDtoRequest;
import com.aibyn.springjwt.exception.CustomNotFoundException;
import com.aibyn.springjwt.exception.ExceptionDescription;
import com.aibyn.springjwt.exception.RepositoryException;
import com.aibyn.springjwt.model.Employee;
import com.aibyn.springjwt.repository.EmployeeRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Optional<Employee> getById(Long id) {
        return this.employeeRepository.findById(id);
    }

    @Override
    public Employee getByIdThrowException(Long id) {
        return this.getById(id).
                orElseThrow(() -> new CustomNotFoundException
                        (String.format(ExceptionDescription.CustomNotFoundException, "Employee", "id", id)));
    }

    @Override
    public List<Employee> getAll() {
        return this.employeeRepository.findAll();
    }

    @Override
    public void create(EmployeeDtoRequest employeeDtoRequest) {
        Employee employee = new Employee();

        employee.setFirstname(employeeDtoRequest.getFirstname());
        employee.setLastname(employeeDtoRequest.getLastname());
        employee.setEmail(employeeDtoRequest.getEmail());
        employee.setPhone(employeeDtoRequest.getPhone());
        employee.setAddress(employeeDtoRequest.getAddress());

        this.employeeRepository.save(employee);
    }

    @Override
    public void update(EmployeeDtoRequest employeeDtoRequest, Long id) {
        Employee employee = this.getByIdThrowException(id);

        if(Strings.isNotBlank(employeeDtoRequest.getFirstname())) employee.setFirstname(employeeDtoRequest.getFirstname());
        if(Strings.isNotBlank(employeeDtoRequest.getLastname())) employee.setLastname(employeeDtoRequest.getLastname());
        if(Strings.isNotBlank(employeeDtoRequest.getEmail())) employee.setEmail(employeeDtoRequest.getEmail());
        if(Strings.isNotBlank(employeeDtoRequest.getPhone())) employee.setPhone(employeeDtoRequest.getPhone());
        if(Strings.isNotBlank(employeeDtoRequest.getAddress())) employee.setAddress(employeeDtoRequest.getAddress());

        try{
            this.employeeRepository.save(employee);
        }catch (Exception e){
            log.error(e);
            throw new RepositoryException(String
                    .format(ExceptionDescription.RepositoryException, "updating", "employee"));
        }

    }

    @Override
    public void delete(Long id) {
        Employee employee = this.getByIdThrowException(id);

        try{
            this.employeeRepository.delete(employee);
        }catch (Exception e){
            log.error(e);
            throw new RepositoryException(String
                    .format(ExceptionDescription.RepositoryException, "deleting", "employee"));
        }

    }
}
