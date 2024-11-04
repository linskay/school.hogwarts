package ru.hogwarts.school.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exception.NotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    private Faculty TEST_FACULTY_1;
    private Faculty TEST_FACULTY_2;
    private Student TEST_STUDENT_1;
    private Student TEST_STUDENT_2;

    @BeforeEach
    void setUp() {
        TEST_FACULTY_1 = new Faculty();
        TEST_FACULTY_1.setId(1L);
        TEST_FACULTY_1.setName("Java");
        TEST_FACULTY_1.setColor("Black");

        TEST_FACULTY_2 = new Faculty();
        TEST_FACULTY_2.setId(2L);
        TEST_FACULTY_2.setName("C++");
        TEST_FACULTY_2.setColor("Brown");

        TEST_STUDENT_1 = new Student();
        TEST_STUDENT_1.setId(1L);
        TEST_STUDENT_1.setName("Oleg");
        TEST_STUDENT_1.setAge(20);
        TEST_STUDENT_1.setFaculty(TEST_FACULTY_1);

        TEST_STUDENT_2 = new Student();
        TEST_STUDENT_2.setId(2L);
        TEST_STUDENT_2.setName("Gennadiy");
        TEST_STUDENT_2.setAge(22);
        TEST_STUDENT_2.setFaculty(TEST_FACULTY_1);
    }

    @Test
    @DisplayName("Создание факультета")
    void createFaculty() {
        when(facultyRepository.save(TEST_FACULTY_1)).thenReturn(TEST_FACULTY_1);

        Faculty createdFaculty = facultyService.createFaculty(TEST_FACULTY_1);

        assertThat(createdFaculty)
                .describedAs("Созданный факультет не null")
                .isNotNull();
        assertThat(createdFaculty)
                .describedAs("Созданный факультет совпадает с ожидаемым")
                .isEqualTo(TEST_FACULTY_1);
    }

    @Test
    @DisplayName("Поиск факультета по ID")
    void findFaculty() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.of(TEST_FACULTY_1));

        Faculty foundFaculty = facultyService.findFaculty(TEST_FACULTY_1.getId());

        assertThat(foundFaculty)
                .describedAs("Найденный факультет не null")
                .isNotNull();
        assertThat(foundFaculty)
                .describedAs("Найденный факультет совпадает с ожидаемым")
                .isEqualTo(TEST_FACULTY_1);
    }

    @Test
    @DisplayName("Поиск несуществующего факультета по ID")
    void findFacultyNotFound() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> facultyService.findFaculty(TEST_FACULTY_1.getId()))
                .describedAs("Должно быть выброшено исключение NotFoundException")
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Faculty not found with ID: [%s]".formatted(TEST_FACULTY_1.getId()));
    }

    @Test
    @DisplayName("Удаление существующего факультета")
    void deleteFaculty() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.of(TEST_FACULTY_1));

        facultyService.deleteFaculty(TEST_FACULTY_1.getId());

        verify(facultyRepository, times(1))
                .deleteById(TEST_FACULTY_1.getId());
    }

    @Test
    @DisplayName("Удаление несуществующего факультета")
    void deleteFacultyNotFound() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> facultyService.deleteFaculty(TEST_FACULTY_1.getId()))
                .describedAs("Должно быть выброшено исключение NotFoundException")
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Faculty not found with ID: [%s]".formatted(TEST_FACULTY_1.getId()));
    }

    @Test
    @DisplayName("Редактирование существующего факультета")
    void editFaculty() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.of(TEST_FACULTY_1));
        when(facultyRepository.save(TEST_FACULTY_1)).thenReturn(TEST_FACULTY_1);

        Faculty editedFaculty = facultyService.editFaculty(TEST_FACULTY_1);

        assertThat(editedFaculty)
                .describedAs("Отредактированный факультет не null")
                .isNotNull();
        assertThat(editedFaculty)
                .describedAs("Отредактированный факультет совпадает с ожидаемым")
                .isEqualTo(TEST_FACULTY_1);
    }

    @Test
    @DisplayName("Редактирование несуществующего факультета")
    void editFacultyNotFound() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> facultyService.editFaculty(TEST_FACULTY_1))
                .describedAs("Должно быть выброшено исключение NotFoundException")
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Faculty not found with ID: [%s]".formatted(TEST_FACULTY_1.getId()));
    }

    @Test
    @DisplayName("Поиск факультетов по цвету")
    void findByColorBetween() {
        when(facultyRepository.findByColorContainingIgnoreCase("Black")).thenReturn(Collections.singletonList(TEST_FACULTY_1));

        List<Faculty> faculties = facultyService.findByColor("Black");

        assertThat(faculties)
                .describedAs("Список факультетов не пустой")
                .isNotEmpty();
        assertThat(faculties)
                .describedAs("Список факультетов должен содержать ожидаемый факультет")
                .containsExactly(TEST_FACULTY_1);
    }

    @Test
    @DisplayName("Получение всех факультетов")
    void findAllFaculty() {
        when(facultyRepository.findAll()).thenReturn(Arrays.asList(TEST_FACULTY_1, TEST_FACULTY_2));

        List<Faculty> faculties = facultyService.findAllFaculty();

        assertThat(faculties)
                .describedAs("Список факультетов не должен быть пустым")
                .isNotEmpty();
        assertThat(faculties)
                .describedAs("Список факультетов должен содержать ожидаемые факультеты")
                .containsExactlyInAnyOrder(TEST_FACULTY_1, TEST_FACULTY_2);
    }

    @Test
    @DisplayName("Получение студентов по факультету")
    void getStudentsByFaculty() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.of(TEST_FACULTY_1));
        when(studentRepository.findAllByFaculty(TEST_FACULTY_1)).thenReturn(Arrays.asList(TEST_STUDENT_1, TEST_STUDENT_2));

        List<Student> students = facultyService.getStudentsByFaculty(TEST_FACULTY_1.getId());

        assertThat(students)
                .describedAs("Список студентов не должен быть пустым")
                .isNotEmpty();
        assertThat(students)
                .describedAs("Список студентов должен содержать ожидаемых студентов")
                .containsExactlyInAnyOrder(TEST_STUDENT_1, TEST_STUDENT_2);
    }

    @Test
    @DisplayName("Получение студентов по несуществующему факультету")
    void getStudentsByFacultyNotFound() {
        when(facultyRepository.findById(TEST_FACULTY_1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> facultyService.getStudentsByFaculty(TEST_FACULTY_1.getId()))
                .describedAs("Должно быть выброшено исключение NotFoundException")
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Faculty not found with ID: [%s]".formatted(TEST_FACULTY_1.getId()));
    }
}