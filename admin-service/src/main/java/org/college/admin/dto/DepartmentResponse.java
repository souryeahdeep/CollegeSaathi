package org.college.admin.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentResponse {
    private Long id;
    private String name;
    private String code;
    private List<CourseResponse> courses;
}

