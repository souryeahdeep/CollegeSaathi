package org.college.student.service;


import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.college.student.dto.StudentDTO;
import org.college.student.entity.Student;
import org.college.student.repo.StudentRepo;
import org.college.student.utils.StudentMapper;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import student.grpc.AttendanceResponse;
import student.grpc.StudentRequest;
import student.grpc.StudentServiceGrpc;

import java.time.Year;
import java.util.*;

@Slf4j
@Service
public class StudentService extends StudentServiceGrpc.StudentServiceImplBase {
    private final StudentRepo studentRepo;
    private final StudentMapper studentMapper;
    private final StudentJwtUtil studentJwtUtil;
    public StudentService(StudentRepo studentRepo, StudentMapper studentMapper, StudentJwtUtil studentJwtUtil) {
        this.studentRepo = studentRepo;
        this.studentMapper = studentMapper;
        this.studentJwtUtil = studentJwtUtil;
    }

    public String addStudent(StudentDTO studentDTO) {
        try {
            String studentId = String.format("JIS/%d/%04d", 2024, studentRepo.count() + 1);
            studentDTO.setStudentId(studentId);
            studentRepo.save(studentMapper.studentDTOToStudent(studentDTO));
            log.info("Student {} has been added", studentId);
            return studentId;
        } catch (Exception e) {
            return "ERROR";
        }
    }

    public Boolean updateStudent(List<StudentDTO> studentDTOList) {
        try {
            for (StudentDTO studentDTO : studentDTOList) {
                studentRepo.deleteById(studentDTO.getStudentId());
                studentRepo.save(studentMapper.studentDTOToStudent(studentDTO));
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean deleteStudent(String studentId) {
        if (studentRepo.existsById(studentId)) {
            studentRepo.deleteById(studentId);
            return true;
        } else {
            return false;
        }

    }

    public List<StudentDTO> fetchStudents(int page) {
        // page is expected as 1-based from caller; clamp to minimum 1
        int pageSize = 20;
        int pageNumber = Math.max(page, 1) - 1; // convert to 0-based for PageRequest

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("studentId").ascending());
        var pageResult = studentRepo.findAll(pageRequest);

        // safe debug print: only print if a student exists
        List<Student> content = pageResult.getContent();
        content.stream().findFirst().ifPresent(s -> System.out.println(s.getStudentName() + " " + s.getSemester()));

        return content.stream().map(studentMapper::studentToStudentDTO).toList();
    }

    public Student fetchStudent(String name, String id) {
        return studentRepo.findByStudentIdAndStudentName(id, name);
    }

    public boolean increaseAttendance(String studentId) {
        Student student = studentRepo.findById(studentId).orElse(null);
        if (student == null) {
            return false;
        } else {
            student.setPresent(student.getPresent() + 1);
            studentRepo.save(student);
            log.info("Attendance Marked Successfully");
        }
        return true;
    }

    public List<StudentDTO> fetchStudentsByBranchAndSem(String branch, Integer sem) {
        return studentRepo.findStudentsByBranch(branch).stream()
                .filter(s -> Objects.equals(s.getSemester(), sem))
                .map(studentMapper::studentToStudentDTO)
                .toList();
    }

    public @Nullable List<StudentDTO> fetchStudentsByBranchSemesterGroupAndSection(String branch, Integer sem, Integer group, Integer section) {
        return Objects.requireNonNull(studentRepo.findByBranchAndGroupAndSectionAndSemester(branch, group, section, sem)).stream().map(studentMapper::studentToStudentDTO).toList();

    }

    @Override
    public void getStudent(StudentRequest request, StreamObserver<AttendanceResponse> responseObserver) {
        Student student = studentRepo.findById(request.getStudentId()).orElse(null);
        if (student == null) {
            responseObserver.onNext(AttendanceResponse.newBuilder().setSuccess(false).build());
            responseObserver.onCompleted();
            return;
        }
        boolean matches = student.getBranch().equals(request.getBranch()) && student.getGroup().equals(Integer.parseInt(request.getGroupNo())) && student.getSection().equals(Integer.parseInt(request.getSectionNo()));
        responseObserver.onNext(AttendanceResponse.newBuilder().setSuccess(matches).build());
        responseObserver.onCompleted();

    }

    public Map<String, Object> loginService(@RequestParam String name, @RequestParam String id){
        Student res = fetchStudent(name,id);
        if(res==null){
            return new HashMap<>();
        }
        Map<String, Object> response = new HashMap<>();
        response.put("student", res);
        response.put("token", studentJwtUtil.generateStudentToken(res.getStudentId()));
        return response;
    }

    public List<StudentDTO> getStudentsWithLowAttendance(Integer attendanceLimit) {
        if (attendanceLimit == null) {
            return Collections.emptyList();
        }

        return studentRepo.findAll().stream()
                .filter(s -> {
                    Integer total = s.getTotalClass();
                    Integer present = s.getPresent();
                    if (present == null) present = 0;
                    // If total is null or zero, treat attendance as 0%
                    if (total == null || total == 0) {
                        return attendanceLimit > 0;
                    }
                    double percent = (present.doubleValue() * 100.0) / total.doubleValue();
                    return percent < attendanceLimit;
                })
                .map(studentMapper::studentToStudentDTO)
                .toList();
    }
}
