package ru.hogwarts.school.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    private FacultyRepository facultyRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private FacultyServiceImpl facultyService;

    @Test
    @DisplayName("Создание факультета")
    void createFaculty() {
        Faculty faculty = TestConstants.TEST_FACULTY;

        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty createdFaculty = facultyService.createFaculty(faculty);

        assertThat(createdFaculty).isEqualTo(faculty);
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    @DisplayName("Поиск факультета по ID")
    void findFaculty() {
        Faculty faculty = TestConstants.TEST_FACULTY;

        when(facultyRepository.findAllById(faculty.getId())).thenReturn(facultyRepository.findAllById(faculty.getId()));

        Faculty foundFaculty = facultyService.findFaculty(faculty.getId());

        assertThat(foundFaculty).isEqualTo(faculty);
        verify(facultyRepository, times(1)).findAllById(faculty.getId());
    }

    @Test
    @DisplayName("Поиск факультета, которого нет")
    void findFaculty_notFound() {
        long facultyId = 1L;

        Faculty foundFaculty = facultyService.findFaculty(facultyId);

        assertThat(foundFaculty).isNull();
    }

    @Test
    @DisplayName("Удаление факультета")
    void deleteFaculty() {
        long facultyId = TestConstants.TEST_FACULTY.getId();

        facultyService.deleteFaculty(facultyId);

        verify(facultyRepository, times(1)).deleteById(facultyId);
    }

    @Test
    @DisplayName("Редактирование факультета")
    void editFaculty() {
        Faculty faculty = TestConstants.TEST_FACULTY;

        when(facultyRepository.save(faculty)).thenReturn(faculty);

        Faculty editedFaculty = facultyService.editFaculty(faculty);

        assertThat(editedFaculty).isEqualTo(faculty);
        verify(facultyRepository, times(1)).save(faculty);
    }

    @Test
    @DisplayName("Поиск факультетов по цвету")
    void findByColorBetween() {
        String color = TestConstants.TEST_FACULTY.getColor();
        List<Faculty> faculties = List.of(
                TestConstants.TEST_FACULTY,
                TestConstants.TEST_FACULTY_2
        );

        when(facultyRepository.findAllByNameContainingIgnoreCase(color)).thenReturn(faculties);

        Collection<Faculty> foundFaculties = facultyService.findByColorBetween(color);

        assertThat(foundFaculties).containsExactlyInAnyOrderElementsOf(faculties);
        verify(facultyRepository, times(1)).findAllByNameContainingIgnoreCase(color);
    }

    @Test
    @DisplayName("Получение всех факультетов")
    void findAllFaculty() {
        List<Faculty> faculties = List.of(
                TestConstants.TEST_FACULTY,
                TestConstants.TEST_FACULTY_2
        );

        when(facultyRepository.findAll()).thenReturn(faculties);

        Collection<Faculty> foundFaculties = facultyService.findAllFaculty();

        assertThat(foundFaculties).containsExactlyInAnyOrderElementsOf(faculties);
        verify(facultyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Получение студентов по факультету")
    void getStudentsByFaculty() {
        long facultyId = TestConstants.TEST_FACULTY.getId();
        Faculty faculty = TestConstants.TEST_FACULTY;

        when(facultyRepository.findAllById(facultyId)).thenReturn(faculty);
        when(studentRepository.findAll(faculty)).thenReturn(List.of());

        Collection<Student> foundStudents = facultyService.getStudentsByFaculty(facultyId);

        assertThat(foundStudents).isEmpty(); // Проверить, что список пустой
        verify(facultyRepository, times(1)).findAllById(facultyId);
        verify(studentRepository, times(1)).findAll(faculty);
    }
}