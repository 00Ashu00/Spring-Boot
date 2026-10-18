package com.learningspringboot.ashu.repositories;
import com.learningspringboot.ashu.entities.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>{

}
