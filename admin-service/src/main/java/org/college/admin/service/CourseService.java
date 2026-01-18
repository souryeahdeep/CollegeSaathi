package org.college.admin.service;

import org.college.admin.dto.ClassEntityDTO;
import org.college.admin.dto.CourseResponse;
import org.college.admin.entity.Course;
import org.college.admin.entity.Department;
import org.college.admin.feign.TeacherInterface;
import org.college.admin.repo.CourseRepo;
import org.college.admin.repo.DepartmentRepo;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepo courseRepo;
    private final DepartmentRepo departmentRepo;
    public CourseService(CourseRepo courseRepo, DepartmentRepo departmentRepo) {
        this.courseRepo = courseRepo;
        this.departmentRepo = departmentRepo;
    }

    public @Nullable List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    public @Nullable Course addCourse(CourseResponse courseDTO) {
        Department department = departmentRepo.findById(courseDTO.getDepartmentId()).get();
        Course course = new Course();
        course.setDepartment(department);
        course.setCourseCode(courseDTO.getCourseCode());
        course.setCredit(courseDTO.getCredit());
        course.setCourseName(courseDTO.getCourseName());
        System.out.println("Adding course " + courseDTO.getCourseCode());
        return courseRepo.save(course);
    }


}
