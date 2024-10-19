package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;
import java.util.List;

public interface FacultyService {
    long createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty deleteFaculty(long id);

    Faculty editFaculty(long id, Faculty faculty);

    List<Faculty> filterByColor(String color);

    Collection<Faculty> findAllFaculty();
}
