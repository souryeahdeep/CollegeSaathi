package org.college.student.utils;


import org.college.student.dto.StudentDTO;
import org.college.student.entity.Student;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface StudentMapper {
    StudentDTO studentToStudentDTO(Student student);
    Student studentDTOToStudent(StudentDTO studentDTO);
}
