package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ro.alina.unidoc.model.StudentModel;
import ro.alina.unidoc.model.filters.StudentFilter;
import ro.alina.unidoc.model.property_editor.GenericPropertyEditor;
import ro.alina.unidoc.service.StudentService;

import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentController {

    private final StudentService studentService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(StudentFilter.class, new GenericPropertyEditor<>(StudentFilter.class));
    }

    /**
     * gets all the students, paginated filtered by study criteria, firstName, lastName
     *
     * @param pageable object that holds the starting number of the page, the number of rows shown on a page and the sort object
     * @param filter   object that holds the fields the students can be filtered by
     * @return returns the current page with the students
     */
    @GetMapping("/all")
    public ResponseEntity<Page<StudentModel>> getAllStudents(final Pageable pageable,
                                                             @RequestParam(name = "filter", required = false) final StudentFilter filter) {
        return ResponseEntity.ok(studentService.getAllStudents(pageable, Optional.ofNullable(filter)
                .orElseGet(StudentFilter::new)));
    }
}
