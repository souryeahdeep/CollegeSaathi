package org.college.admin.controller;

import org.college.admin.entity.Department;
import org.college.admin.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Department>> getAllDepartments(){
        try{
            return ResponseEntity.ok().body(departmentService.getAllDepartments());
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Department> addDepartment(@RequestBody Department department){
        try {
            return ResponseEntity.ok().body(departmentService.addDepartment(department));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
