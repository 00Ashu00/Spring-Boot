package com.learning.spring_data_jpa_practice.contoller;

import com.learning.spring_data_jpa_practice.entity.Employee;
import com.learning.spring_data_jpa_practice.projection.EmployeeSummary;
import com.learning.spring_data_jpa_practice.repository.EmployeeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;


    @RestController
    @RequestMapping("/api/employees")
    public class EmployeeController {

        private final EmployeeRepository employeeRepository;
        public EmployeeController(EmployeeRepository employeeRepository) {
            this.employeeRepository = employeeRepository;
        }

        @GetMapping
        public List<Employee> getAllEmployees() {
            return employeeRepository.findAll();
        }

        @PostMapping
        public Employee postEmployee(@RequestBody Employee employee) {
            return  employeeRepository.save(employee);
        }
        @PutMapping("/{id}")
        public Employee putEmployee(
                @PathVariable Long id,
                @RequestBody Employee employee) {

            employee.setId(id);
            return employeeRepository.save(employee);
        }
        @DeleteMapping("/{id}")
        public void deleteEmployeeById( @PathVariable Long id) {
            employeeRepository.deleteById(id);
        }
        @GetMapping("/search")
        public List<Employee> findByName(@RequestParam String name) {
            return employeeRepository.findByName(name);
        }
        @GetMapping("/salary/above")
        public List<Employee> findBySalaryAbove(@RequestParam Double salary){
            return employeeRepository.findBySalaryGreaterThan(salary);
        }
        @GetMapping("/salary/below")
        public List<Employee> findBySalaryBelow(@RequestParam Double salary){
            return employeeRepository.findBySalaryLessThan(salary);
        }
        @GetMapping("/sorted")
        public List<Employee> getSortedEmployees(
                @RequestParam(defaultValue = "name") String field,
                @RequestParam(defaultValue = "ASC") String direction) {

            Sort sort = direction.equalsIgnoreCase("DESC")
                    ? Sort.by(field).descending()
                    : Sort.by(field).ascending();

            return employeeRepository.findAll(sort);
        }
        @GetMapping("/page")
        public Page<Employee> getPageEmployees(@RequestParam(defaultValue="0") int page,
                                               @RequestParam(defaultValue = "5") int size){
            Pageable p=PageRequest.of(page,size);
            return employeeRepository.findAll(p);
        }
        @GetMapping("/summary")
        public List<EmployeeSummary> getEmployeeSummaries() {
            return employeeRepository.findAllProjectedBy();
        }




    }

