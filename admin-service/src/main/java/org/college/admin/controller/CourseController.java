package org.college.admin.controller;

import org.college.admin.dto.ClassEntityDTO;
import org.college.admin.dto.CourseResponse;
import org.college.admin.entity.Course;
import org.college.admin.feign.TeacherInterface;
import org.college.admin.repo.CourseRepo;
import org.college.admin.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/course")
@RestController
public class CourseController {
    private final CourseService courseService;
    private final TeacherInterface teacherInterface;
    public CourseController(CourseService courseService, TeacherInterface teacherInterface) {
        this.courseService = courseService;
        this.teacherInterface = teacherInterface;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    @PostMapping("/add")
    public ResponseEntity<Course> addCourse(@RequestBody CourseResponse courseDTO) {
        return ResponseEntity.ok().body(courseService.addCourse(courseDTO));
    }
    @PostMapping("/assign")
    public ResponseEntity<String> assignCourse(@RequestBody List<ClassEntityDTO> classEntityDTO) {
        return teacherInterface.addClass(classEntityDTO);
    }
}
