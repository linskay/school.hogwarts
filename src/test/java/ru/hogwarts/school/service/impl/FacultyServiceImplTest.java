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
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public
class FacultyServiceImplTest {

    @Mock
    FacultyRepository facultyRepository;
    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    FacultyServiceImpl facultyService;

    private Faculty TEST_FACULTY;
    private Faculty TEST_FACULTY2;

    private Student TEST_STUDENT;
    private Student TEST_STUDENT2;

    @BeforeEach
    void setUp() {
        TEST_FACULTY = new Faculty();
        TEST_FACULTY.setId(1L);
        TEST_FACULTY.setName("Java");
        TEST_FACULTY.setColor("Black");

        TEST_FACULTY2 = new Faculty();
        TEST_FACULTY2.setId(2L);
        TEST_FACULTY2.setName("Python");
        TEST_FACULTY2.setColor("Brawn");

        TEST_STUDENT = new Student();
        TEST_STUDENT.setId(1L);
        TEST_STUDENT.setName("Duck");
        TEST_STUDENT.setAge(15);
        TEST_STUDENT.setFaculty(TEST_FACULTY);

        TEST_STUDENT2 = new Student();
        TEST_STUDENT2.setId(2L);
        TEST_STUDENT2.setName("Goose");
        TEST_STUDENT2.setAge(18);
        TEST_STUDENT2.setFaculty(TEST_FACULTY);
    }

    @Test
    @DisplayName("Создание факультета")
    void createFaculty() {

        when(facultyRepository.save(TEST_FACULTY)).thenReturn(TEST_FACULTY);

        Faculty savedFaculty = facultyService.createFaculty(TEST_FACULTY);

        when(facultyRepository.findById(TEST_FACULTY.getId())).thenReturn(Optional.of(TEST_FACULTY));

        Faculty actual = facultyService.findFaculty(savedFaculty.getId());

        assertThat(actual).withFailMessage("Факультет = null").isNotNull();
        assertThat(actual).withFailMessage("Значение != ожидаемому").isEqualTo(savedFaculty);
    }

    @Test
    @DisplayName("Поиск несуществующего факультета")
    void findFacultyNotFound() {

        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> facultyService.findFaculty(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Факультет не найден с этим ID: [%s]".formatted(TEST_FACULTY.getId()));
    }

    @Test
    @DisplayName("Удаление существующего факультета")
    void deleteFaculty() {

        when(facultyRepository.existsById(TEST_FACULTY.getId())).thenReturn(true);

        facultyService.deleteFaculty(TEST_FACULTY.getId());

        verify(facultyRepository, times(1)).deleteById(TEST_FACULTY.getId());
    }

    @Test
    @DisplayName("Удаление несуществующего факультета")
    void deleteFacultyNotFound() {

        when(facultyRepository.existsById(TEST_FACULTY.getId())).thenReturn(false);

        assertThatThrownBy(() -> facultyService.deleteFaculty(TEST_FACULTY.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Факультет не найден с этим ID: [%s]".formatted(TEST_FACULTY.getId()));

        verify(facultyRepository, never().description("Удаление выполнено(когда не должно быть)")).deleteById(TEST_FACULTY.getId());
    }

    @Test
    @DisplayName("Редактирование существующего факультета")
    void editFaculty() {

        when(facultyRepository.existsById(TEST_FACULTY.getId())).thenReturn(true);

        when(facultyRepository.save(TEST_FACULTY)).thenReturn(TEST_FACULTY);

        Faculty editedFaculty = facultyService.editFaculty(TEST_FACULTY);

        assertThat(editedFaculty).withFailMessage("Факультет = null").isNotNull();
        assertThat(editedFaculty).withFailMessage("Значение != ожидаемому").isEqualTo(TEST_FACULTY);
    }

    @Test
    @DisplayName("Редактирование несуществующего факультета")
    void editFacultyNotFound() {

        when(facultyRepository.existsById(TEST_FACULTY.getId())).thenReturn(false);

        assertThatThrownBy(() -> facultyService.editFaculty(TEST_FACULTY))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("%s not found with ID: [%s]".formatted(Faculty.class.getSimpleName(), TEST_FACULTY.getId()));

        verify(facultyRepository, never().description("Перезапись данных выполнена(когда не должно быть)")).save(TEST_FACULTY);
    }

    @Test
    @DisplayName("Получение студентов по факультету")
    void getStudentsByFaculty() {

        when(facultyRepository.findById(TEST_FACULTY.getId())).thenReturn(Optional.of(TEST_FACULTY));

        when(studentRepository.findAllByFaculty(TEST_FACULTY)).thenReturn(Arrays.asList(TEST_STUDENT, TEST_STUDENT2));

        Collection<Student> students = facultyService.getStudentsByFaculty(TEST_FACULTY.getId());

        assertThat(students).withFailMessage("Результат пустой").isNotEmpty();
        assertThat(students).withFailMessage("Результат не содержит ожидаемых результатов").containsExactlyInAnyOrder(TEST_STUDENT, TEST_STUDENT2);
    }

    @Test
    @DisplayName("Получение всех факультетов")
    void findAllFaculty() {

        when(facultyRepository.findAll()).thenReturn(Arrays.asList(TEST_FACULTY, TEST_FACULTY2));

        Collection<Faculty> faculties = facultyService.findAllFaculty();

        assertThat(faculties).withFailMessage("Результат пустой").isNotEmpty();
        assertThat(faculties).withFailMessage("Результат не содержит ожидаемых результатов").containsExactlyInAnyOrder(TEST_FACULTY, TEST_FACULTY2);
    }

    @Test
    @DisplayName("Поиск факультетов по цвету")
    void findByColorBetween() {

        when(facultyRepository.findByColorContainingIgnoreCase(TEST_FACULTY.getColor())).thenReturn(Collections.singletonList(TEST_FACULTY));

        Collection<Faculty> faculties = facultyService.findByColorBetween(TEST_FACULTY.getColor());

        assertThat(faculties).withFailMessage("Результат пустой").isNotEmpty();
        assertThat(faculties).withFailMessage("Результат не содержит ожидаемых результатов").containsExactly(TEST_FACULTY);
    }
}