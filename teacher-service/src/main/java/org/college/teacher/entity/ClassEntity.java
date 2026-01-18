package org.college.teacher.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "class")
@Setter
@Getter
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "dummy")
    private int dummyId;

    @Column(name = "stream")
    private String stream;

    @Column(name = "teacher_id")
    private String teacherId;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "section_no")
    private String sectionNo;

    @Column(name = "group_no")
    private String groupNo;

    @Column(name="day")
    private String day;

    @Column(name = "timing")
    private LocalTime startTime;

}
