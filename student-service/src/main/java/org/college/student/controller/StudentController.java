package org.college.student.controller;


import org.college.student.dto.StudentDTO;
import org.college.student.entity.Student;
import org.college.student.service.StudentJwtUtil;
import org.college.student.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5173","http://localhost:5174"})
@RequestMapping("/student")
@RestController
public class StudentController {
    private final StudentService studentService;
    StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    // APIs used by Admin
    @PostMapping("/add")
    public ResponseEntity<Boolean> addStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(studentDTO));
    }

    // GET /student/get?branch=...&year=...
    @GetMapping(value = "/get", params = {"branch", "year"})
    public ResponseEntity<List<StudentDTO>> getAllStudents(@RequestParam String branch, @RequestParam Integer year) {
        return ResponseEntity.ok().body(studentService.fetchStudentsByBranchAndYear(branch, year));
    }

    // GET /student/get?branch=...&year=...&group=...&section=...
    @GetMapping(value = "/get")
    public ResponseEntity<List<StudentDTO>> getAllStudents(@RequestParam String branch,
                                                        @RequestParam Integer sem,
                                                        @RequestParam Integer group,
                                                        @RequestParam Integer section) {
        return ResponseEntity.ok().body(studentService.fetchStudentsByBranchSemesterGroupAndSection(branch, sem, group, section));
    }


    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok().body(studentService.fetchStudents());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteStudent(@RequestParam String id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.deleteStudent(id));
    }

    // APIs used by Admin and Student
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateStudent(@RequestBody List<StudentDTO> studentDTOS) {
        return ResponseEntity.ok().body(studentService.updateStudent(studentDTOS));

    }

    // APIs used by Student
    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> loginStudent(@RequestParam String name, @RequestParam String id){
        return ResponseEntity.ok().body(studentService.loginService(name,id));
    }

    //APIs used by Teacher
    @PostMapping("/increase-attendance")
    public ResponseEntity<Boolean> increaseAttendance(@RequestParam String studentId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.increaseAttendance(studentId));
    }



}
