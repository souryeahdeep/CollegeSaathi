package org.college.teacher.service;

import lombok.extern.slf4j.Slf4j;
import org.college.teacher.entity.*;
import org.college.teacher.repo.AttendanceRecordRepo;
import org.college.teacher.repo.AttendanceSessionRepository;
import org.college.teacher.utils.QrCodeGenerator;
import org.college.teacher.utils.QrJwtUtil;
import org.college.teacher.utils.StudentJwtUtil;
import org.springframework.stereotype.Service;
import student.grpc.StudentServiceGrpc;
import student.grpc.StudentRequest;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class AttendanceService {
    private final QrJwtUtil jwtUtil;
    private final StudentAttendanceGrpcClient studentAttendanceServiceGrpc;
    private final QrCodeGenerator qrCodeGenerator;
    private final AttendanceSessionRepository sessionRepository;
    private final AttendanceRecordRepo attendanceRecordRepo;
    private final StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub;

    public AttendanceService(StudentJwtUtil studentJwtUtil, QrCodeGenerator qrCodeGenerator,
                             AttendanceRecordRepo attendanceRecordRepo, AttendanceSessionRepository sessionRepository, QrJwtUtil jwtUtil, StudentAttendanceGrpcClient studentAttendanceServiceGrpc, StudentServiceGrpc.StudentServiceBlockingStub studentServiceBlockingStub
    ) {
        this.qrCodeGenerator = qrCodeGenerator;
        this.sessionRepository = sessionRepository;
        this.attendanceRecordRepo = attendanceRecordRepo;
        this.jwtUtil = jwtUtil;
        this.studentAttendanceServiceGrpc = studentAttendanceServiceGrpc;
        this.studentServiceBlockingStub = studentServiceBlockingStub;
    }


    public byte[] startAttendance(ClassEntity classEntity) throws Exception {

        String attendanceId = UUID.randomUUID().toString();

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime expiry = start.plusMinutes(10);

        AttendanceSession session = new AttendanceSession();
        session.setAttendanceId(attendanceId);
        session.setTeacherId(classEntity.getTeacherId());
        session.setClassId(classEntity.getSubjectCode());
        session.setStream(classEntity.getStream());
        session.setGroupNo(classEntity.getGroupNo());
        session.setSectionNo(classEntity.getSectionNo());
        int sem = classEntity.getSubjectCode().charAt(classEntity.getSubjectCode().length() - 3);

        session.setSemester(String.valueOf(sem));
        session.setStartTime(start);
        session.setExpiryTime(expiry);

        sessionRepository.save(session);

        String token = jwtUtil.generateQrToken(session);
        return qrCodeGenerator.generateQrCode(token);
    }

    public boolean scanAttendance(
            StudentScanRequest request) {
        Map<String, Object> attendanceSession = jwtUtil.validateAndGetAttendanceId(request.getQrToken());
        AttendanceSession session = sessionRepository.findById((String) attendanceSession.get("attendanceId"))
                .orElseThrow(() -> new RuntimeException("Invalid attendance session"));

        if (LocalDateTime.now().isAfter(session.getExpiryTime())) {
            log.info("Attendance session has expired");
            return false;
        }
        if (attendanceRecordRepo.existsByAttendanceIdAndStudentId((String) attendanceSession.get("attendanceId"), request.getStudentId())) {
            log.info("Attendance has already been scan");
             return true;
        }
        AttendanceRecord record = new AttendanceRecord();
        record.setAttendanceId(attendanceSession.get("attendanceId").toString());
        record.setStudentId(request.getStudentId());
        record.setLatitude(request.getLatitude());
        record.setLongitude(request.getLongitude());
        record.setScannedAt(LocalDateTime.now());
        StudentRequest studentRequest = StudentRequest.newBuilder()
                .setStudentId(request.getStudentId())
                .setBranch(attendanceSession.get("classId").toString())
                .setGroupNo(attendanceSession.get("group").toString())
                .setSectionNo(attendanceSession.get("section").toString())
                .setSemester(attendanceSession.get("semester").toString()).build();
        System.out.println(studentRequest.getSectionNo()+" "+studentRequest.getGroupNo()+" "+studentRequest.getStudentId());
        if (!studentServiceBlockingStub.getStudent(studentRequest).getSuccess()) {
            log.info("Student not found");
            return false;
        }
        attendanceRecordRepo.save(record);
        return studentAttendanceServiceGrpc.increaseAttendance(request.getStudentId()).getSuccess();
    }

}
