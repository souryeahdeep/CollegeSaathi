package org.college.admin.dto;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AdminDTO {
    @EqualsAndHashCode.Include
    private String id;
    private String name;
}
