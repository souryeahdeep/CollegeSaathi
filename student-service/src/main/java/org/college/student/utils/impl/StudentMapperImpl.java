package org.college.student.utils.impl;

import org.college.student.dto.StudentDTO;
import org.college.student.entity.Student;
import org.college.student.utils.StudentMapper;
import org.springframework.stereotype.Component;

@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDTO studentToStudentDTO(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setStudentName(student.getStudentName());
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setGroup(student.getGroup());
        studentDTO.setSection(student.getSection());
        studentDTO.setTotalClass(student.getTotalClass());
        studentDTO.setPresent(student.getPresent());
        studentDTO.setBranch(student.getBranch());
        studentDTO.setSemester(student.getSemester());
        studentDTO.setRollNo(student.getRollNo());
        studentDTO.setRegistrationNo(student.getRegistrationNo());
        studentDTO.setPresentAddress(student.getPresentAddress());
        studentDTO.setCity(student.getCity());
        studentDTO.setPin(student.getPin());
        studentDTO.setMobileNo(student.getMobileNo());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        studentDTO.setBloodGroup(student.getBloodGroup());

        return studentDTO;
    }

    @Override
    public Student studentDTOToStudent(StudentDTO studentDTO) {
        if (studentDTO == null) {
            return null;
        }

        Student student = new Student();
        student.setStudentName(studentDTO.getStudentName());
        student.setStudentId(studentDTO.getStudentId());
        student.setGroup(studentDTO.getGroup());
        student.setSection(studentDTO.getSection());
        student.setTotalClass(studentDTO.getTotalClass());
        student.setPresent(studentDTO.getPresent());
        student.setBranch(studentDTO.getBranch());
        student.setSemester(studentDTO.getSemester());
        student.setRollNo(studentDTO.getRollNo());
        student.setRegistrationNo(studentDTO.getRegistrationNo());
        student.setPresentAddress(studentDTO.getPresentAddress());
        student.setCity(studentDTO.getCity());
        student.setPin(studentDTO.getPin());
        student.setMobileNo(studentDTO.getMobileNo());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setBloodGroup(studentDTO.getBloodGroup());

        return student;
    }
}
