package org.college.student.service;

import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class StudentAttendanceGrpcService
        extends student.grpc.StudentAttendanceServiceGrpc.StudentAttendanceServiceImplBase {

    private final StudentService studentService; // business logic

    public StudentAttendanceGrpcService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void increaseAttendance(
            student.grpc.IncreaseAttendanceRequest request,
            StreamObserver<student.grpc.IncreaseAttendanceResponse> responseObserver) {
        ManagedChannelBuilder.forAddress("Student-Service", 9090)
                .useTransportSecurity() // ✅ must be here
                .build();


        responseObserver.onNext(
                student.grpc.IncreaseAttendanceResponse.newBuilder()
                        .setSuccess(studentService.increaseAttendance(request.getStudentId()))
                        .build()
        );
        responseObserver.onCompleted();
    }
}
