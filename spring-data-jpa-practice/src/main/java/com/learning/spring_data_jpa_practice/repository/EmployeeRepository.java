package com.learning.spring_data_jpa_practice.repository;

import com.learning.spring_data_jpa_practice.entity.Employee;
import com.learning.spring_data_jpa_practice.projection.EmployeeSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);
    List<Employee> findByEmail(String email);
    List<Employee> findBySalaryGreaterThan(double salary);
    List<Employee> findBySalaryLessThan(double salary);
    List<Employee> findAllByOrderByNameAsc();
    List<Employee> findAllByOrderBySalaryDesc();
    Page<Employee> findAll(Pageable pageable);
    List<EmployeeSummary> findAllProjectedBy();

}
