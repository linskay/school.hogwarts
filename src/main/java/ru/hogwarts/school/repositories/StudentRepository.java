package ru.hogwarts.school.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.model.StudentProjection;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByAgeBetween(int minAge, int maxAge);

    List<Student> findAllByFaculty(Faculty faculty);

    List <Student> findByNameContainsIgnoreCase(String name);

    @Query(value = "SELECT COUNT(*) FROM Student", nativeQuery = true)
    long countAllStudents();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    Double getAverageAge();

    @Query("SELECT s FROM Student s ORDER BY s.id DESC")
    Page<StudentProjection> findLastFiveStudents(Pageable pageable);
}
