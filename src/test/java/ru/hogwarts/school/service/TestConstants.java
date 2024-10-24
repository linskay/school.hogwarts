package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

public class TestConstants {
    public static final Faculty TEST_FACULTY = new Faculty(1L, "java", "black");

    public static final Faculty TEST_FACULTY_2 = new Faculty(2L, "php", "green");

    public static final Student TEST_STUDENT = new Student(1L, 11, "Rick");

    public static final Student TEST_STUDENT_2 = new Student(2L, 17, "Morty");
}
