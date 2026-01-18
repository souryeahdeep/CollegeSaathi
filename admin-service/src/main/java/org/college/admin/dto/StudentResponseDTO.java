package org.college.admin.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class StudentResponseDTO {

    private String studentName;
    @EqualsAndHashCode.Include
    private String studentId;
    private Integer group;
    private Integer section;
    private Integer totalClass;
    private Integer present;
    private String branch;
    @EqualsAndHashCode.Include
    private Long rollNo;
    @EqualsAndHashCode.Include
    private String registrationNo;
    private String presentAddress;
    private String city;
    private Integer pin;
    private String mobileNo;
    private String dateOfBirth;
    private String bloodGroup;

}
