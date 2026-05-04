package org.college.student.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "students")
@Entity
@Getter
@Setter
public class Student {
    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_id")
    @Id
    private String studentId;

    @Column(name = "group_no")
    private Integer group;

    @Column(name = "section_no")
    private Integer section;

    @Column(name = "total_class")
    private Integer totalClass;

    @Column(name = "present")
    private Integer present;

    @Column(name = "semester")
    private Integer semester;

    @Column(name="branch")
    private String branch;

    @Column(name = "roll_no")
    private Long rollNo;

    @Column(name = "registration_no")
    private String registrationNo;

    @Column(name = "present_address")
    private String presentAddress;

    @Column(name = "city")
    private String city;

    @Column(name="pin")
    private Integer pin;

    @Column(name = "mobile_no")
    private String mobileNo;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "blood_group")
    private String bloodGroup;
}
