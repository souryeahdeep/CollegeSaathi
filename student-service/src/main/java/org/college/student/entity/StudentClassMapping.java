package org.college.student.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "student_class_mapping")
public class StudentClassMapping {

    @Id
    Long  id;
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "class_group_id")
    private Long classGroupId;
}
