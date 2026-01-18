package org.college.teacher;

import org.college.teacher.service.StudentAttendanceGrpcClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import student.grpc.StudentAttendanceServiceGrpc;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class TeacherApplication {


    public static void main(String[] args) {
		SpringApplication.run(TeacherApplication.class, args);
	}

}
