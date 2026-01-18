package org.college.teacher.service;

import org.springframework.stereotype.Service;
import student.grpc.IncreaseAttendanceRequest;
import student.grpc.IncreaseAttendanceResponse;
import student.grpc.StudentAttendanceServiceGrpc;


@Service
public class StudentAttendanceGrpcClient  {

    private final StudentAttendanceServiceGrpc.StudentAttendanceServiceBlockingStub stub;

    public StudentAttendanceGrpcClient(
            StudentAttendanceServiceGrpc.StudentAttendanceServiceBlockingStub stub) {
        this.stub = stub;
    }

    public IncreaseAttendanceResponse increaseAttendance(String studentId) {
        IncreaseAttendanceRequest request =
                IncreaseAttendanceRequest.newBuilder()
                        .setStudentId(studentId)
                        .build();

        return stub
                .increaseAttendance(request);
    }
}

