package org.college.teacher.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.college.teacher.dto.TeacherDTO;
import org.college.teacher.entity.Teacher;
import org.college.teacher.entity.TeacherLoginDetails;
import org.college.teacher.repo.TeacherRepo;
import org.college.teacher.service.TeacherService;
import org.college.teacher.utils.TeacherMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepo teacherRepo;
    private final TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepo teacherRepo, TeacherMapper teacherMapper) {
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
     * @param teacherId
     */
    public boolean removeTeacher(String teacherId) {

        Optional<Teacher> teacherOptional = teacherRepo.findById(teacherId);
        System.out.println(teacherOptional+" "+teacherOptional.isPresent());
        if(teacherOptional.isPresent()) {
            System.out.println(teacherId);
            teacherRepo.deleteById(teacherId);
            log.info("Teacher with id {} has been removed", teacherId);
            return true;
        }else{
            log.info("Teacher with id {} has NOT been removed", teacherId);
            return false;
        }
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


