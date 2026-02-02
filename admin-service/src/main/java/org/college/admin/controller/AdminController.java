package org.college.admin.controller;

import org.college.admin.dto.AdminDTO;
import org.college.admin.dto.StudentResponseDTO;
import org.college.admin.entity.Admin;
import org.college.admin.dto.TeacherResponseDTO;
import org.college.admin.feign.StudentInterface;
import org.college.admin.feign.TeacherInterface;
import org.college.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

@CrossOrigin(value = {"http://localhost:5173", "http://localhost:5174"})
@RequestMapping("/admin")
@RestController
public class AdminController {
    private final AdminService adminService;
    private final TeacherInterface teacherInterface;
    private final StudentInterface studentInterface;

    public AdminController(AdminService adminService, TeacherInterface teacherInterface,StudentInterface studentInterface) {
        this.adminService = adminService;
        this.teacherInterface = teacherInterface;
        this.studentInterface = studentInterface;
    }

    // APIs for Teacher
    @GetMapping("/teacher/{page}")
    public ResponseEntity<List<TeacherResponseDTO>> getAllTeachers(@PathVariable Integer page) {
        try{
            List<TeacherResponseDTO> teacherResponseDTOS = teacherInterface.getAllTeachers(page).getBody();
            return new ResponseEntity<>(teacherResponseDTOS, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/teacher/add")
    public ResponseEntity<String> addTeacher(@RequestBody List<TeacherResponseDTO> teacherDTOList) {

        teacherInterface.addTeacher(teacherDTOList);
        return ResponseEntity.ok("Added Successfully");
    }

    @PutMapping("/teacher/update")
    ResponseEntity<String> updateTeacher(@RequestBody TeacherResponseDTO teacherDTO){
        try{
            teacherInterface.updateTeacher(teacherDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
            return ResponseEntity.ok("Updated Successfully");
    }

    @DeleteMapping("/teacher/delete/JIS/{year}/{id}")
    ResponseEntity<String> removeTeacher(@PathVariable String year, @PathVariable String id){
        try{
            teacherInterface.removeTeacher(year,id);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok("Deleted Successfully");
    }

    //APIs for Students
    @GetMapping("/student")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
       try {
           List<StudentResponseDTO> studentResponseDTO = studentInterface.getAllStudents().getBody();
           return new ResponseEntity<>(studentResponseDTO, HttpStatus.OK);
       }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping("/student/add")
    public ResponseEntity<String> addStudent(@RequestBody StudentResponseDTO studentResponseDTO) {
        try{
            studentInterface.addStudent(studentResponseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to add student");
        }
        return ResponseEntity.ok("Added Successfully");
    }

    @PutMapping("/student/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentResponseDTO studentResponseDTO) {
        try{
            studentInterface.updateStudent(studentResponseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to update Student");
        }
        return ResponseEntity.ok("Added Successfully");
    }

    @DeleteMapping("/student/remove")
    public ResponseEntity<String> removeStudent(@RequestBody String studentId) {
        try {
            studentInterface.deleteStudent(studentId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to delete student");
        }
        return ResponseEntity.ok("Removed Successfully");
    }

    // APIs for Admin
    @PostMapping("/{id}")
    public ResponseEntity<Boolean> adminLogin(@PathVariable String id) {
        if (adminService.isAdminPresent(id)) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(false);
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Admin>> getAllAdmin() {
        return ResponseEntity.ok().body(adminService.fetchAllAdmin());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addAdmin(@RequestBody AdminDTO adminDTO) {
        if (adminService.isAdminPresent(adminDTO.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Admin already exists");
        } else {
            adminService.addAdmin(adminDTO);
        }
        return ResponseEntity.ok("Saved Successfully");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateAdmin(@RequestBody AdminDTO teacherDTO) {
        return ResponseEntity.ok().body("GET");
    }

}
