package org.college.teacher.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ClassEntityDTO {

    private String stream;
    private String teacherId;
    private String subjectCode;
    private String sectionNo;
    private String groupNo;
    private String day;
    private LocalTime startTime;

}
