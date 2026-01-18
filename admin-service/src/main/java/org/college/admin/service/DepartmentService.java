package org.college.admin.service;

import org.college.admin.entity.Department;
import org.college.admin.repo.DepartmentRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DepartmentService {

    private final DepartmentRepo departmentRepo;
    public DepartmentService(DepartmentRepo departmentRepo) {
        this.departmentRepo = departmentRepo;
    }
    public @Nullable List<Department> getAllDepartments(){
        return departmentRepo.findAll();
    }

    public Department addDepartment(Department department) {

        return departmentRepo.save(department);
    }
}
