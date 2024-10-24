//package ru.hogwarts.school.service;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import ru.hogwarts.school.exception.FacultyNotFoundException;
//import ru.hogwarts.school.model.Faculty;
//import ru.hogwarts.school.repositories.FacultyRepository;
//import ru.hogwarts.school.service.impl.FacultyServiceImpl;
//
//import java.util.Collection;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.*;
//import static ru.hogwarts.school.service.TestConstants.TEST_FACULTY;
//import static ru.hogwarts.school.service.TestConstants.TEST_FACULTY_2;
//
//
//class FacultyServiceImplTest {
//    @Mock
//    FacultyServiceImpl facultyServiceImpl;
//    @InjectMocks
//    FacultyRepository facultyRepository;
//    Faculty faculty;
//
//
//    @DisplayName("Положительный тест на создание факультета")
//    @Test
//    void createFaculty() {
//        long expected = facultyServiceImpl.createFaculty(TEST_FACULTY).getId();
//
//        assertThat(expected).isOne();
//        Faculty actual = facultyServiceImpl.findFaculty(expected);
//        assertThat(actual).isEqualTo(TEST_FACULTY);
//    }
//
//    @DisplayName("Положительный тест на удаление факультета")
//    @Test
//    void deleteFaculty() {
//        long targetId = TEST_FACULTY.getId();
//        facultyServiceImpl.createFaculty(TEST_FACULTY);
//
//        facultyServiceImpl.deleteFaculty(targetId);
//
//        assertThat(targetId).isEqualTo(TEST_FACULTY);
//        Faculty actual = facultyServiceImpl.findFaculty(TEST_FACULTY.getId());
//        assertThat(actual).isNull();
//    }
//
////    @DisplayName("Негативный тест на поиск факультета")
////    @Test
////    void negativeFind() {
////
////        Faculty actual = facultyServiceImpl.findFaculty(25);
////
////        assertThat(actual).isNull();
////    }
////
////    @DisplayName("Положительный тест на поиск факультета")
////    @Test
////    void findFaculty() {
////
////        facultyServiceImpl.createFaculty(TEST_FACULTY);
////
////        Faculty actualFind = facultyServiceImpl.findFaculty(TEST_FACULTY.getId());
////
////        assertThat(actualFind).isEqualTo(TEST_FACULTY);
////    }
////
////    @DisplayName("Положительный тест на обновление факультета")
////    @Test
////    void editFaculty() {
////
////        long targetId = TEST_FACULTY.getId();
////        facultyServiceImpl.createFaculty(TEST_FACULTY);
////
////        Faculty oldFaculty = facultyServiceImpl.editFaculty(TEST_FACULTY_2);
////
////        assertThat(oldFaculty).isEqualTo(TEST_FACULTY_2);
////        Faculty actual = facultyServiceImpl.findFaculty(targetId);
////        assertThat(actual).isEqualTo(TEST_FACULTY_2.getId());
////    }
////
////    @DisplayName("Положительный тест на поиск всех факультетов")
////    @Test
////    void findAll() {
////
////        facultyServiceImpl.createFaculty(TEST_FACULTY);
////        facultyServiceImpl.createFaculty(TEST_FACULTY_2);
////        List<Faculty> expected = List.of(TEST_FACULTY, TEST_FACULTY_2);
////
////        Collection<Faculty> actual = facultyServiceImpl.findAllFaculty();
////
////        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
////    }
////
////    @DisplayName("Положительный тест на сортировку факультета по цвету")
////    @Test
////    void filterByColor() {
////        facultyServiceImpl.createFaculty(TEST_FACULTY);
////
////        Collection<Faculty> actual = facultyServiceImpl.filterByColor(TEST_FACULTY.getColor());
////
////        assertThat(actual).containsExactly(TEST_FACULTY);
////    }
//}