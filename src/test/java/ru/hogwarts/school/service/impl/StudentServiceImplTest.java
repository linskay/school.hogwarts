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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student TEST_STUDENT_1;
    private Student TEST_STUDENT_2;
    private Faculty TEST_FACULTY;

    @BeforeEach
    void setUp() {
        TEST_FACULTY = new Faculty();
        TEST_FACULTY.setId(1L);
        TEST_FACULTY.setName("Test Faculty");
        TEST_FACULTY.setColor("Red");

        TEST_STUDENT_1 = new Student();
        TEST_STUDENT_1.setId(1L);
        TEST_STUDENT_1.setName("Test Student 1");
        TEST_STUDENT_1.setAge(20);
        TEST_STUDENT_1.setFaculty(TEST_FACULTY);

        TEST_STUDENT_2 = new Student();
        TEST_STUDENT_2.setId(2L);
        TEST_STUDENT_2.setName("Test Student 2");
        TEST_STUDENT_2.setAge(22);
        TEST_STUDENT_2.setFaculty(TEST_FACULTY);
    }

    @Test
    @DisplayName("Создание студента")
    void createStudent() {
        when(studentRepository.save(TEST_STUDENT_1)).thenReturn(TEST_STUDENT_1);

        Student createdStudent = studentService.createStudent(TEST_STUDENT_1);

        assertThat(createdStudent).isNotNull();
        assertThat(createdStudent).isEqualTo(TEST_STUDENT_1);
    }

    @Test
    @DisplayName("Поиск студента по ID")
    void findStudent() {
        when(studentRepository.getById(TEST_STUDENT_1.getId())).thenReturn(TEST_STUDENT_1);

        Student foundStudent = studentService.findStudent(TEST_STUDENT_1.getId());

        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent).isEqualTo(TEST_STUDENT_1);
    }

    @Test
    @DisplayName("Редактирование существующего студента")
    void editStudent() {
        when(studentRepository.existsById(TEST_STUDENT_1.getId())).thenReturn(true);
        when(studentRepository.save(TEST_STUDENT_1)).thenReturn(TEST_STUDENT_1);

        Student editedStudent = studentService.editStudent(TEST_STUDENT_1);

        assertThat(editedStudent).isNotNull();
        assertThat(editedStudent).isEqualTo(TEST_STUDENT_1);
    }

    @Test
    @DisplayName("Редактирование несуществующего студента")
    void editStudentNotFound() {
        when(studentRepository.existsById(TEST_STUDENT_1.getId())).thenReturn(false);

        assertThatThrownBy(() -> studentService.editStudent(TEST_STUDENT_1))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Student not found with ID: [%s]".formatted(TEST_STUDENT_1.getId()));
    }

    @Test
    @DisplayName("Удаление существующего студента")
    void deleteStudent() {
        when(studentRepository.findById(TEST_STUDENT_1.getId())).thenReturn(Optional.of(TEST_STUDENT_1));

        studentService.deleteStudent(TEST_STUDENT_1.getId());

        verify(studentRepository, times(1)).deleteById(TEST_STUDENT_1.getId());
    }

    @Test
    @DisplayName("Удаление несуществующего студента")
    void deleteStudentNotFound() {
        when(studentRepository.findById(TEST_STUDENT_1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.deleteStudent(TEST_STUDENT_1.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Student not found with ID: [%s]".formatted(TEST_STUDENT_1.getId()));
    }

    @Test
    @DisplayName("Поиск студентов по возрасту в диапазоне")
    void findByAgeBetween() {
        when(studentRepository.findByAgeBetween(20, 22)).thenReturn(Arrays.asList(TEST_STUDENT_1, TEST_STUDENT_2));

        List<Student> students = studentService.findByAgeBetween(20, 22);

        assertThat(students).isNotEmpty();
        assertThat(students).containsExactlyInAnyOrder(TEST_STUDENT_1, TEST_STUDENT_2);
    }

    @Test
    @DisplayName("Получение всех студентов")
    void findAllStudent() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(TEST_STUDENT_1, TEST_STUDENT_2));

        List<Student> students = studentService.findAllStudent();

        assertThat(students).isNotEmpty();
        assertThat(students).containsExactlyInAnyOrder(TEST_STUDENT_1, TEST_STUDENT_2);
    }

    @Test
    @DisplayName("Получение факультета по ID студента")
    void getFacultyByStudentId() {
        when(studentRepository.findById(TEST_STUDENT_1.getId())).thenReturn(Optional.of(TEST_STUDENT_1));

        Faculty faculty = studentService.getFacultyByStudentId(TEST_STUDENT_1.getId());

        assertThat(faculty).isNotNull();
        assertThat(faculty).isEqualTo(TEST_FACULTY);
    }

    @Test
    @DisplayName("Получение факультета по ID несуществующего студента")
    void getFacultyByStudentIdNotFound() {
        when(studentRepository.findById(TEST_STUDENT_1.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getFacultyByStudentId(TEST_STUDENT_1.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Student not found with ID: [%s]".formatted(TEST_STUDENT_1.getId()));
    }
}