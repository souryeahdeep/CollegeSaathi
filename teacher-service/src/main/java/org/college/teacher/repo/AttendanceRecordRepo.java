package org.college.teacher.repo;

import org.college.teacher.entity.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRecordRepo
            extends JpaRepository<AttendanceRecord, Long> {

        boolean existsByAttendanceIdAndStudentId(
                String attendanceId,
                String studentId
        );
    }


