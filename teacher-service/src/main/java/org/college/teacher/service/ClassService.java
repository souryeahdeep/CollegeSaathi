package org.college.teacher.service;


import org.college.teacher.dto.ClassEntityDTO;
import org.college.teacher.entity.ClassEntity;
import org.college.teacher.repo.ClassRepo;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClassService {
    public final ClassRepo classRepo;
    ClassService(ClassRepo classRepo){
        this.classRepo = classRepo;
    }

    public List<ClassEntity> allottedClasses(String id) {
        DayOfWeek dayOfWeek=DayOfWeek.from(LocalDate.now());
        String day=dayOfWeek.toString();
        day=day.charAt(0)+day.substring(1).toLowerCase();
        System.out.println(id+" "+day);
        return classRepo.findByTeacherIdAndDay(id,day);
    }

    public String addClass(List<ClassEntityDTO> classEntitiesDTO){
        try{
            for (ClassEntityDTO classEntityDTO : classEntitiesDTO) {
                ClassEntity classEntity = new ClassEntity();
                classEntity.setDay(classEntityDTO.getDay());
                classEntity.setStream(classEntityDTO.getStream());
                classEntity.setTeacherId(classEntityDTO.getTeacherId());
                classEntity.setGroupNo(classEntityDTO.getGroupNo());
                classEntity.setSectionNo(classEntityDTO.getSectionNo());
                classEntity.setSubjectCode(classEntityDTO.getSubjectCode());
                classEntity.setStartTime(classEntityDTO.getStartTime());
                System.out.println(classEntity.toString());
                classRepo.save(classEntity);
            }
        }catch(Exception e){
            return  e.getMessage();
        }
        return "Added Successfully";
    }

}
