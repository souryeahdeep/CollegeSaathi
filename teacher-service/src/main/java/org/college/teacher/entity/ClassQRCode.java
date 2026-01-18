package org.college.teacher.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassQRCode {
    private String teacherId;
    private String classId;
    private String group;
    private String section;
    private String branch;
    private String year;
}
