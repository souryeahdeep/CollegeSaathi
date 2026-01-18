package org.college.teacher.repo;

import org.college.teacher.entity.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceSessionRepository extends JpaRepository<AttendanceSession,String> {

}
