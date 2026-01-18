package org.college.admin.feign;

import org.college.admin.dto.StudentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("STUDENT-SERVICE")
public interface StudentInterface {
    @PostMapping("/student/add")
    ResponseEntity<String> addStudent(@RequestBody StudentResponseDTO studentDTO);

    @GetMapping("/student/get")
    ResponseEntity<List<StudentResponseDTO>> getAllStudents(@RequestParam String branch, @RequestParam Integer year);

        @GetMapping("/student")
        ResponseEntity<List<StudentResponseDTO>> getAllStudents();

    @DeleteMapping("/student/delete")
    ResponseEntity<String> deleteStudent(@RequestParam String id);

    // APIs used by Admin and Student
    @PutMapping("/student/update")
    ResponseEntity<String> updateStudent(@RequestBody StudentResponseDTO studentDTO);
}
