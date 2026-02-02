package org.college.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.TreeSet;

@SpringBootApplication
@EnableEurekaServer // Make this project as Eureka Server
public class ServiceRegistryApplication {

    public static void main(String[] args) {

        SpringApplication.run(ServiceRegistryApplication.class, args);
    }

}
