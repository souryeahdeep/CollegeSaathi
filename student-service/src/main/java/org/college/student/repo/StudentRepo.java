package org.college.student.repo;


import org.college.student.entity.Student;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepo extends JpaRepository<Student,String> {
    List<Student> findBySectionAndGroup(Integer section, Integer group);

    Student findByStudentIdAndStudentName(String studentId, String studentName);

    List<Student> findStudentsByBranch(String branch);

    @Nullable List<Student> findByBranchAndGroupAndSectionAndSemester(String branch, Integer group, Integer section, Integer sem);
}
