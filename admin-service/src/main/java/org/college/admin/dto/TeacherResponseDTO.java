package org.college.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class TeacherResponseDTO {
    @EqualsAndHashCode.Include
    private String teacherId;
    private String teacherName;
    @EqualsAndHashCode.Include
    private String teacherEmail;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate teacherDateOfBirth;
}
