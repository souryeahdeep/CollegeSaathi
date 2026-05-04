package org.college.teacher.service;

import org.college.teacher.dto.TeacherDTO;
import org.college.teacher.entity.Teacher;
import org.college.teacher.entity.TeacherLoginDetails;

import java.util.List;

public interface TeacherService {
    boolean addTeacher(TeacherDTO teacherDTO);
    List<TeacherDTO> getTeachers(int page);
    void updateTeacher(TeacherDTO teacherDTO);
    boolean removeTeacher(String teacherId);
    public boolean exists(TeacherLoginDetails teacherLoginDetails);
}
