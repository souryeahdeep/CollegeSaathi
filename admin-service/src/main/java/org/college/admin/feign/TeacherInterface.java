package org.college.admin.feign;

import org.college.admin.dto.ClassEntityDTO;
import org.college.admin.dto.TeacherResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("TEACHER-SERVICE")
public interface TeacherInterface {
    @GetMapping("/teacher/{page}")
    ResponseEntity<List<TeacherResponseDTO>> getAllTeachers(@PathVariable int page);

    @PostMapping("/teacher/add")
    ResponseEntity<String> addTeacher(@RequestBody List<TeacherResponseDTO> teacherDTOList);

    @PutMapping("/teacher/update")
    ResponseEntity<String> updateTeacher(@RequestBody TeacherResponseDTO teacherDTO);

    @DeleteMapping("/teacher/delete")
    ResponseEntity<String> removeTeacher(@RequestParam String teacherId);

    @PostMapping("/teacher/assignClass")
    ResponseEntity<String> addClass(@RequestBody List<ClassEntityDTO> classEntityList);
}
