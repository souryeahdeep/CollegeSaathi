package org.college.teacher.repo;


import org.college.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, String> {
    Teacher findByTeacherNameAndTeacherId(String name, String oldPassword);

    Teacher findTeacherByTeacherId(String id);

    void deleteByTeacherId(String teacherId);


}
