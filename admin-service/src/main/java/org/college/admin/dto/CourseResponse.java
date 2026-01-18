package org.college.admin.dto;

import lombok.Data;
import org.college.admin.entity.Course;
import org.college.admin.entity.Department;

@Data
public class CourseResponse {
    private Long id;
    private String courseName;
    private String courseCode;
    private String credit;
    private Long departmentId;
}
