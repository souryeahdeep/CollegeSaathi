package org.college.teacher.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "attendance_session")
public class AttendanceSession {
    @Id
    private String attendanceId;

    private String stream;
    private String teacherId;
    private String classId;
    private String sectionNo;
    private String groupNo;
    private String semester;
    private LocalDateTime startTime;
    private LocalDateTime expiryTime;



}
