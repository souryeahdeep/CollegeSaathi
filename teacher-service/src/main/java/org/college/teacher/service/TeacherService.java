package org.college.teacher.service;


import org.college.teacher.dto.TeacherDTO;
import org.college.teacher.entity.Teacher;
import org.college.teacher.entity.TeacherLoginDetails;
import org.college.teacher.repo.TeacherRepo;
import org.college.teacher.utils.TeacherMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepo teacherRepo;
    private final TeacherMapper teacherMapper;


    public TeacherService(TeacherRepo teacherRepo, TeacherMapper teacherMapper) {
        this.teacherRepo = teacherRepo;
        this.teacherMapper = teacherMapper;
    }

    /**
     * Add Teacher to the Repo
     * @param teacherDTO
     * @return boolean if true saved, false if it doesn't exist
     */
    public boolean addTeacher(TeacherDTO teacherDTO) {
        if (teacherRepo.existsById(teacherDTO.getTeacherId())) {
            return false;
        }
        Teacher teacher = teacherMapper.teacherDTOToteacher(teacherDTO);
        teacherRepo.save(teacher);
        return true;
    }

    /**
     * @param page
     * @return List of Teachers
     */
    public List<TeacherDTO> getTeachers(int page) {
        PageRequest pageable = PageRequest.of(page - 1, 5);
        List<Teacher> teachers = teacherRepo.findAll(pageable).stream().toList();
        return teachers.stream().map(teacherMapper::teacherToTeacherDTO).toList();
    }

    /**
     * Updates Teacher Infos
     * @param teacherDTO
     */
    public void updateTeacher(TeacherDTO teacherDTO) {
        teacherRepo.deleteById(teacherDTO.getTeacherId());
        Teacher teacher = teacherMapper.teacherDTOToteacher(teacherDTO);
        teacherRepo.save(teacher);
    }

    /**
     * Remove Teacher from the Repo
     * @param mailId
     */
    public boolean removeTeacher(String mailId) {
        return teacherRepo.deleteByTeacherEmail(mailId);
    }

    /**
     * Change the Password
     * @param name
     * @param oldPassword
     * @param newPassword
     * @return boolean
     */
    public boolean changePassword(String name, String oldPassword, String newPassword) {
        Teacher teacher = teacherRepo.findByTeacherNameAndTeacherId(name,oldPassword);
            if(teacher!=null) {
                PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                teacher.setPassword(passwordEncoder.encode(newPassword));
                teacherRepo.save(teacher);
                return true;
            }else{
                return false;
            }

    }

    /**
     * Check if teacher exists
     * @param teacherLoginDetails
     * @return boolean
     */
    public boolean exists(TeacherLoginDetails teacherLoginDetails) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Teacher storedHashPass = teacherRepo.findTeacherByTeacherId(teacherLoginDetails.getId());
        return passwordEncoder.matches(teacherLoginDetails.getPassword(), storedHashPass.getPassword());
    }
}


