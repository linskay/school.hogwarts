package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty createFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    void deleteFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    Collection<Faculty> filterByColor(String color);

    Collection<Faculty> findAllFaculty();
}
