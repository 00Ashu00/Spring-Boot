package com.learningspringboot.ashu.controllers;

import com.learningspringboot.ashu.services.DepartmentService;

import org.springframework.web.bind.annotation.*;
import com.learningspringboot.ashu.entities.DepartmentEntity;
import java.util.List;
import jakarta.validation.Valid;

@RestController
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/departments/{id}")
    public DepartmentEntity getDepartmentById(@PathVariable Long id) {
       return departmentService.getDepartmentById(id);
    }

    @GetMapping("/departments")
    public List<DepartmentEntity> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    @PostMapping("/departments")
    public DepartmentEntity createDepartment(
           @Valid @RequestBody DepartmentEntity department) {

        return departmentService.createDepartment(department);
    }

    @PutMapping("/departments")
    public DepartmentEntity updateDepartment(
          @Valid  @RequestBody DepartmentEntity department) {
       return  departmentService.updateDepartment(department);
    }
    @DeleteMapping("/departments")
    public void deleteDepartment(
            @RequestBody DepartmentEntity department){
        departmentService.deleteDepartment(department.getId());
    }

}