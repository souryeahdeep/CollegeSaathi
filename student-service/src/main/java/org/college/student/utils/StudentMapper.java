package org.college.student.utils;


import org.college.student.dto.StudentDTO;
import org.college.student.entity.Student;
import org.college.student.utils.impl.StudentMapperImpl;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


public interface StudentMapper  {
    StudentDTO studentToStudentDTO(Student student);
    Student studentDTOToStudent(StudentDTO studentDTO);
}
