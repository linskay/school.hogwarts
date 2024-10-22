//package ru.hogwarts.school.service;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import ru.hogwarts.school.model.Student;
//import ru.hogwarts.school.service.impl.StudentServiceImpl;
//
//import java.util.Collection;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.assertj.core.api.Assertions.not;
//import static ru.hogwarts.school.service.TestConstants.TEST_STUDENT;
//import static ru.hogwarts.school.service.TestConstants.TEST_STUDENT_2;
//
//class StudentServiceImplTest {
//    public StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
//
//    @DisplayName("Положительный тест на создание студента")
//    @Test
//    void createStudent() {
//        long expected = studentServiceImpl.createStudent(TEST_STUDENT);
//
//        assertThat(expected).isOne();
//        Student actual = studentServiceImpl.findStudent(expected);
//        assertThat(actual).isEqualTo(TEST_STUDENT);
//    }
//
//
//    @DisplayName("Положительный тест на поиск студента")
//    @Test
//    void findStudent() {
//        long targetId = TEST_STUDENT.getId();
//        studentServiceImpl.createStudent(TEST_STUDENT);
//
//        Long actualFind = studentServiceImpl.findStudent(targetId).getId();
//
//        assertThat(actualFind).isEqualTo(targetId);
//    }
//
//    @DisplayName("Негативный тест на поиск студента")
//    @Test
//    void negativeFind() {
//
//        Student actual = studentServiceImpl.findStudent(25);
//
//        assertThat(actual).isNull();
//    }
//
//    @DisplayName("Положительный тест на обновление студента")
//    @Test
//    void editStudent() {
//        long targetId = TEST_STUDENT.getId();
//        studentServiceImpl.createStudent(TEST_STUDENT);
//
//        Student oldStudent = studentServiceImpl.editStudent(targetId, TEST_STUDENT_2);
//
//        assertThat(oldStudent).isEqualTo(TEST_STUDENT_2);
//        Student actual = studentServiceImpl.findStudent(targetId);
//        assertThat(actual).isEqualTo(TEST_STUDENT_2.getId());
//    }
//
//
//    @DisplayName("Положительный тест на удаление студента")
//    @Test
//    void deleteStudent() {
//        long targetId = TEST_STUDENT.getId();
//        studentServiceImpl.createStudent(TEST_STUDENT);
//
//        Long deleteStudent = studentServiceImpl.deleteStudent(targetId).getId();
//
//        assertThat(deleteStudent).isEqualTo(targetId);
//    }
//
//    @DisplayName("Положительный тест на сортировку по возрасту")
//    @Test
//    void filterByAge() {
//
//        studentServiceImpl.createStudent(TEST_STUDENT);
//
//        Collection<Student> actual = studentServiceImpl.filterByAge(TEST_STUDENT.getAge());
//
//        assertThat(actual).containsExactly(TEST_STUDENT);
//    }
//
//    @DisplayName("Положительный тест на поиск всех факультетов")
//    @Test
//    void findAll() {
//
//        studentServiceImpl.createStudent(TEST_STUDENT);
//        studentServiceImpl.createStudent(TEST_STUDENT_2);
//        List<Student> expected = List.of(TEST_STUDENT, TEST_STUDENT_2);
//
//        Collection<Student> actual = studentServiceImpl.findAllStudent();
//
//        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
//    }
//}