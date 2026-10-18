package com.learningspringboot.ashu.services;

import com.learningspringboot.ashu.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;
import com.learningspringboot.ashu.entities.DepartmentEntity;
import java.util.List;
import com.learningspringboot.ashu.exceptions.DepartmentNotFoundException;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    public List<DepartmentEntity> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public DepartmentEntity getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() ->
                        new DepartmentNotFoundException("Department not found"));
    }
    public DepartmentEntity createDepartment(DepartmentEntity department) {
       return departmentRepository.save(department);
    }
    public DepartmentEntity updateDepartment(DepartmentEntity department) {
        return departmentRepository.save(department);
    }
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

}
