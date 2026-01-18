package org.college.teacher.utils;


import org.college.teacher.dto.TeacherDTO;
import org.college.teacher.entity.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherDTO teacherToTeacherDTO(Teacher teacher);

    Teacher teacherDTOToteacher(TeacherDTO teacherDTO);
}
