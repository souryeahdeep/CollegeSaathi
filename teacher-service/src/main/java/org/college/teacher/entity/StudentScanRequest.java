package org.college.teacher.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentScanRequest {
        private String qrToken;
        private String studentId;
        private Double latitude;
        private Double longitude;

}
