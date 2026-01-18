package org.college.teacher.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Setter
@Getter
@Table(name = "teachers")
@Entity
public class Teacher {

    @Id
    @Column(name = "teacher_id", nullable = false, unique = true)
    private String teacherId;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "password")
    private String password;

    @Column(name = "branch")
    private String branch;

    @Column(name = "teacher_email")
    private String teacherEmail;

    @Column(name = "teacher_date_of_birth")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate teacherDateOfBirth;
}
