package com.ads.springbootweb.repositories;

import com.ads.springbootweb.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends JpaRepository<EmployeeEntity, Long> {

    Long id(Long id);

}
