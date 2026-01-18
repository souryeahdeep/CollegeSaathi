package org.college.teacher.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "attendance_record",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"attendanceId", "studentId"})
        }
)
@Getter
@Setter
public class AttendanceRecord {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String attendanceId;
        private String studentId;

        private Double latitude;
        private Double longitude;

        private LocalDateTime scannedAt;


}
