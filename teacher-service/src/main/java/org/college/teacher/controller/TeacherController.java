package org.college.teacher.controller;

import org.college.teacher.dto.ClassEntityDTO;
import org.college.teacher.dto.TeacherDTO;
import org.college.teacher.entity.ClassEntity;
import org.college.teacher.entity.StudentScanRequest;
import org.college.teacher.entity.TeacherLoginDetails;
import org.college.teacher.service.AttendanceService;
import org.college.teacher.service.ClassService;
import org.college.teacher.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
@RequestMapping("/teacher")
@RestController
public class TeacherController {
    private final ClassService classService;
    private final TeacherService teacherService;
    private final AttendanceService attendanceService;
    public TeacherController(ClassService classService, TeacherService teacherService, AttendanceService attendanceService) {
        this.classService = classService;
        this.teacherService = teacherService;
        this.attendanceService = attendanceService;
    }
    //  APIs used by Admin

    /**
     *
     * Accept the Page Number and return 5 teacher for each page.
     * @return List of Teachers
     */
    @GetMapping("/{page}")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers(@PathVariable int page) {

        List<TeacherDTO> teachers = teacherService.getTeachers(page);
        return ResponseEntity.ok(teachers);
    }

    /**
     *
     * Adds Teacher to the DB
     * @return Success status with a message
     */
    @PostMapping("/add")
    public ResponseEntity<String> addTeacher(@RequestBody List<TeacherDTO> teacherDTOList) {
        for (TeacherDTO teacherDTO : teacherDTOList) {
            if (!teacherService.addTeacher(teacherDTO)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("TEACHER IS PRESENT ALREADY");
            }
        }

        return ResponseEntity.ok("Added Successfully");
    }

    /**
     *
     * Updates Teacher's Details
     * @return Success Status with a message
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateTeacher(@RequestBody TeacherDTO teacherDTO) {
        try{
            teacherService.updateTeacher(teacherDTO);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Modified");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot update");
        }


    }

    /**
     *
     * Removes a teacher by accepting its Mail ID.
     * @return Success Status with a message
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> removeTeacher(@RequestParam String mailId) {
        try {
            if(teacherService.removeTeacher(mailId)) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("Removed Successfully");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Unable to remove Teacher");
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Removed Successfully");
    }

    /**
     *
     * Accepts class, and adds to the DB
     * @return Status with Message
     */
    @PostMapping("/assignClass")
    public ResponseEntity<String> assignClass(@RequestBody List<ClassEntityDTO> classEntityList) {
        try{
           return ResponseEntity.status(HttpStatus.ACCEPTED).body(classService.addClass(classEntityList));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot add Class");
        }
    }

    //  APIs used by Teacher

    /**
     *
     * Accepts Teacher ID and Password, checks if the teacher is present and matches the passwords
     * @return Allotted classes for the present Day
     */
    @PostMapping("/login")
    public ResponseEntity<List<ClassEntity>> allottedClasses(@RequestBody TeacherLoginDetails teacherLoginDetails) {
        if(teacherService.exists(teacherLoginDetails)){
            List<ClassEntity> classEntities = classService.allottedClasses(teacherLoginDetails.getId());
            return new ResponseEntity<>(classEntities, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    /**
     *
     * @param name
     * @param currentPassword
     * @param newPassword
     * @return Success status with boolean.
     */
    @PostMapping("/change-password")
    public ResponseEntity<Boolean> changePassword(@RequestParam String name, @RequestParam String currentPassword, @RequestParam String newPassword) {
       try {
           if(teacherService.changePassword(name,currentPassword,newPassword))
               return ResponseEntity.ok().body(true);
       }catch (Exception e){
           return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
       }
       return ResponseEntity.status(HttpStatus.CONFLICT).body(false);
    }

    /**
     *
     * @param classEntity
     * @return QR Code for Attendance
     * @throws Exception
     */
    @PostMapping("/start")
    public ResponseEntity<byte[]> startAttendance(
            @RequestBody ClassEntity classEntity
    ) throws Exception {
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(attendanceService.startAttendance(classEntity));
    }

    /**
     *
     * @param studentScanRequest
     * @return Success student along with boolean
     * @throws Exception
     */
    @PostMapping("/attendance/scan")
    public ResponseEntity<Boolean> validateAttendance(@RequestBody StudentScanRequest studentScanRequest) throws Exception {
        boolean res=attendanceService.scanAttendance(studentScanRequest);
        if(!res){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        return ResponseEntity.ok(true);
    }


}

