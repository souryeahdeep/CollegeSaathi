package org.college.teacher.repo;


import org.college.teacher.entity.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassRepo extends JpaRepository<ClassEntity,Integer> {
    List<ClassEntity> findByTeacherIdAndDay(String teacher_id, String day);
}
