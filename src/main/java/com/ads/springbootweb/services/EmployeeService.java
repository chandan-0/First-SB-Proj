package com.ads.springbootweb.services;

import com.ads.springbootweb.dto.EmployeeDTO;
import com.ads.springbootweb.entities.EmployeeEntity;
import com.ads.springbootweb.exception.ResourceNotFoundException;
import com.ads.springbootweb.repositories.EmployeesRepository;
import org.apache.el.util.ReflectionUtil;
import org.aspectj.util.Reflection;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeesRepository employeesRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeesRepository employeesRepository, ModelMapper modelMapper) {
        this.employeesRepository = employeesRepository;
        this.modelMapper = modelMapper;
    }


    public Optional<EmployeeDTO> getEmployeeById(Long employeeId) {
        Optional<EmployeeEntity> employeeEntity = employeesRepository.findById(employeeId);
        Optional<EmployeeDTO> employeeDTO = employeeEntity.map(employeeEntity1 -> modelMapper.map(employeeEntity1, EmployeeDTO.class));
        return employeeDTO;
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities = employeesRepository.findAll();

        return employeeEntities.stream().map(employeeEntity -> modelMapper.map(employeeEntity, EmployeeDTO.class)).collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        return modelMapper.map(employeesRepository.saveAndFlush(modelMapper.map(inputEmployee, EmployeeEntity.class)), EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(Long employeeId, EmployeeDTO inputEmployee) {
        boolean exists = isExistsByEmployeeId(employeeId);
        if (!exists) throw new ResourceNotFoundException("Id Not found" + " -> " + employeeId);

        EmployeeEntity employeeEntity = modelMapper.map(inputEmployee, EmployeeEntity.class);
        employeeEntity.setId(employeeId);
        EmployeeEntity savedEmployee = employeesRepository.save(employeeEntity);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    public boolean isExistsByEmployeeId(Long id) {
        return employeesRepository.existsById(id);
    }

    public boolean deleteEmployeeById(Long employeeId) {
        try {
            boolean exists = isExistsByEmployeeId(employeeId);
            if (!exists) return false;
            employeesRepository.deleteById(employeeId);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public EmployeeDTO updatePartialEmployeeById(Long employeeId, Map<String, Object> inputEmployee) {
        boolean exists = isExistsByEmployeeId(employeeId);
        if (!exists) return null;

        EmployeeEntity employeeEntity = employeesRepository.findById(employeeId).get();

        inputEmployee.forEach((field, value) -> {
            Field fieldToBeUpdate = ReflectionUtils.findField(EmployeeEntity.class, field);
            fieldToBeUpdate.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdate, employeeEntity, value);
        });
        return modelMapper.map(employeesRepository.save(employeeEntity), EmployeeDTO.class);
    }
}
