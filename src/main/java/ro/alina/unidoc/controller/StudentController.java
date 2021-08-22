package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.alina.unidoc.model.RequiredDocumentsRowModel;
import ro.alina.unidoc.model.Response;
import ro.alina.unidoc.model.StudentModel;
import ro.alina.unidoc.model.filters.StudentFilter;
import ro.alina.unidoc.model.property_editor.GenericPropertyEditor;
import ro.alina.unidoc.service.StudentService;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
@CrossOrigin(origins = "*", maxAge = 3600)
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

    @GetMapping("/secretary/document")
    public ResponseEntity<List<RequiredDocumentsRowModel>> getRequiredSecretaryDocuments(@RequestParam(value = "studentId") final Long studentId){
        return ResponseEntity.ok(studentService.getRequiredSecretaryDocuments(studentId));
    }

    @PostMapping("/document/secretary/upload")
    public ResponseEntity<Response> uploadStudentDocument(@RequestPart(value = "file") MultipartFile file,
                                                          @RequestParam(value = "studentId") Long studentId,
                                                          @RequestParam(value = "secretaryDocumentId") Long secretaryDocumentId,
                                                          @RequestParam(value = "name") String name) {
        return ResponseEntity.ok(studentService.uploadSecretaryDocument(file, studentId, secretaryDocumentId, name));
    }
}
