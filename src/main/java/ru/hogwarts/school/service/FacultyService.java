package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;

public interface FacultyService {

    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(Long id);

    void deleteFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    List<Faculty> findByColor(String color);

    Collection<Faculty> findAllFaculty();

    Collection<Student> getStudentsByFaculty(long id);
}
