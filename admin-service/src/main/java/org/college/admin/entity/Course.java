package org.college.admin.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "course")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String courseCode;
    private String credit;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    @JsonBackReference   // or @JsonIgnore
    private Department department;
}
