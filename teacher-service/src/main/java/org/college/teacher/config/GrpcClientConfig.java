package org.college.teacher.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import student.grpc.StudentAttendanceServiceGrpc;
import student.grpc.StudentServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @Bean
    ManagedChannel studentServiceChannel() {
        return ManagedChannelBuilder
                .forAddress("localhost", 9090) // 👈 service name / DNS
                .usePlaintext()
                .build();
    }

    @Bean
    StudentAttendanceServiceGrpc.StudentAttendanceServiceBlockingStub
    studentAttendanceStub(ManagedChannel studentServiceChannel) {

        return StudentAttendanceServiceGrpc
                .newBlockingStub(studentServiceChannel);
    }

    @Bean
    StudentServiceGrpc.StudentServiceBlockingStub
    studentServiceBlockingStub(ManagedChannel studentServiceChannel) {

        return StudentServiceGrpc
                .newBlockingStub(studentServiceChannel);
    }
}


