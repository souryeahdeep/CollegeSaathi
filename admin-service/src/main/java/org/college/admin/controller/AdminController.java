package org.college.admin.controller;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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

    @DeleteMapping("/teacher/delete")
    ResponseEntity<String> removeTeacher(@RequestParam String teacherId){
        try{
            log.info("Teaacher Id : {}", teacherId);
            teacherInterface.removeTeacher(teacherId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok("Deleted Successfully");
    }

    //APIs for Students
    @GetMapping("/student/{page}")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(@PathVariable int page) {
       try {
           List<StudentResponseDTO> studentResponseDTO = studentInterface.getAllStudents(page).getBody();
           return new ResponseEntity<>(studentResponseDTO, HttpStatus.OK);
       }catch (Exception e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/student/get")
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(@RequestParam String branch,@RequestParam Integer semester) {
        try {
            List<StudentResponseDTO> studentResponseDTO = studentInterface.getAllStudents(branch, semester).getBody();
            return new ResponseEntity<>(studentResponseDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/student/add")
    public ResponseEntity<String> addStudent(@RequestBody StudentResponseDTO studentResponseDTO) {
        ResponseEntity<String> res;
        try{
             res = studentInterface.addStudent(studentResponseDTO);

             log.info(res.toString());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to add student");
        }
        return res;
    }

    @PutMapping("/student/update")
    public ResponseEntity<String> updateStudent(@RequestBody StudentResponseDTO studentResponseDTO) {
        try{
            studentInterface.updateStudent(studentResponseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to update Student");
        }
        return ResponseEntity.ok("UPDATED Successfully");
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

    @GetMapping("/student/getStudentsWithLowAttendance")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsWithLowAttendance(@RequestParam Integer attendanceLimit) {
        try {
            List<StudentResponseDTO> studentResponseDTO = studentInterface.getStudentsWithLowAttendance(attendanceLimit).getBody();
            return new ResponseEntity<>(studentResponseDTO, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // APIs for Admin
    @GetMapping("/{id}")
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
    public ResponseEntity<String> updateAdmin(@RequestBody AdminDTO adminDTO) {
        return ResponseEntity.ok().body(adminService.updateAdmin(adminDTO));
    }

}
