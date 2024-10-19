package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.impl.FacultyServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@Tag(name = "Контроллер факультетов", description = "Контроллеры для добавления факультетов")
public class FacultyController {

    private final FacultyServiceImpl facultyServiceImpl;

    public FacultyController(FacultyServiceImpl facultyServiceImpl) {
        this.facultyServiceImpl = facultyServiceImpl;
    }

    /**
     *
     * @param id идентификатор факультета
     * @param faculty факультет для поиска
     * @return возвращает id факультета, если найден
     */
    @GetMapping("{id}/find-faculty")
    @Operation(summary = "Ищет факультет",
            description = "Ищет факультет по id",
            responses = {@ApiResponse(responseCode = "404", description = "Факультет не найден"),
                    @ApiResponse(responseCode = "200", description = "Факультет найден")})
    public long getFindFaculty(@PathVariable long id,
                               @RequestBody Faculty faculty) {
        return facultyServiceImpl.findFaculty(id).getId();

    }

    /**
     *
     * @param faculty факультет для добавления
     * @return возвращает созданный факультет
     */

    @PostMapping("/add-faculty")
    @Operation(summary = "Создает факультет",
            description = "Создает новый факультет",
            responses = @ApiResponse(responseCode = "200", description = "Факультет создан"))
    public long createFaculty(@RequestBody Faculty faculty) {
        return facultyServiceImpl.createFaculty(faculty);
    }

    /**
     *
     * @param id идентификатор факультета
     * @param faculty факультет для обновления
     * @return новый факультет
     */

    @PutMapping("{id}/update")
    @Operation(summary = "Обновляет факультет",
            description = "Обновляет факультет и устанавливает id",
            responses = {@ApiResponse(responseCode = "404", description = "Факультет не найден"),
                    @ApiResponse(responseCode = "200", description = "Факультет найден")})
    public Faculty editFaculty(@PathVariable("id") long id,
                               @RequestBody Faculty faculty) {
        return facultyServiceImpl.editFaculty(id, faculty);
    }

    /**
     *
     * @param id факультета для удаления
     * @return возвращает удаленный факультет (тип Faculty) или null, если факультет с заданным id не был найден
     */

    @DeleteMapping("{id}/delete")
    @Operation(summary = "Удаляет факультет",
            description = "Удаляет факультет по id",
            responses = {@ApiResponse(responseCode = "404", description = "Факультет для удаления не найден"),
                    @ApiResponse(responseCode = "200", description = "Факультет удален")})
    public Faculty deleteFaculty(@PathVariable("id") long id) {
        return facultyServiceImpl.deleteFaculty(id);
    }

    /**
     *
     * @param color цвет, по которому будет поиск для фильтрации. String
     * @return Метод filterByColor возвращает список Faculty (тип List<Faculty>), которые имеют заданный цвет (color)
     */

    @GetMapping("/color")
    @Operation(summary = "Ищет по цвету",
            description = "Ищет факультет по цвету, возвращает коллекцию",
            responses = {@ApiResponse(responseCode = "404", description = "Факультет не найден"),
                    @ApiResponse(responseCode = "200", description = "Факультет найден")})
    public List<Faculty> filterColor(@PathVariable("color") String color) {
        return facultyServiceImpl.filterByColor(color);
    }
}
