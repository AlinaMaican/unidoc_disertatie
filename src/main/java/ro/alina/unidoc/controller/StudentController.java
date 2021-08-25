package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.alina.unidoc.model.*;
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<Page<StudentModel>> getAllStudents(final Pageable pageable,
                                                             @RequestParam(name = "filter", required = false) final StudentFilter filter) {
        return ResponseEntity.ok(studentService.getAllStudents(pageable, Optional.ofNullable(filter)
                .orElseGet(StudentFilter::new)));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/document/secretary")
    public ResponseEntity<List<RequiredDocumentsRowModel>> getRequiredSecretaryDocuments(@RequestParam(value = "studentId") final Long studentId) {
        return ResponseEntity.ok(studentService.getRequiredSecretaryDocuments(studentId));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/document/secretary/upload")
    public ResponseEntity<Response> uploadStudentDocument(@RequestPart(value = "file") MultipartFile file,
                                                          @RequestParam(value = "studentId") Long studentId,
                                                          @RequestParam(value = "secretaryDocumentId") Long secretaryDocumentId,
                                                          @RequestParam(value = "name") String name) {
        return ResponseEntity.ok(studentService.uploadSecretaryDocument(file, studentId, secretaryDocumentId, name));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/document/own")
    public ResponseEntity<Page<StudentDocumentModel>> getOwnDocuments(@RequestParam(value = "studentId") final Long studentId,
                                                                      @RequestParam(value = "pageSize") final int pageSize,
                                                                      @RequestParam(value = "pageNumber") final int pageNumber) {
        return ResponseEntity.ok(studentService.getOwnDocuments(studentId, pageNumber, pageSize));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/document/own/upload")
    public ResponseEntity<Response> uploadOwnStudentDocument(@RequestPart(value = "file") MultipartFile file,
                                                             @RequestParam(value = "studentId") Long studentId,
                                                             @RequestParam(value = "description") String description,
                                                             @RequestParam(value = "name") String name) {
        return ResponseEntity.ok(studentService.uploadOwnDocument(file, studentId, description, name));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/document/notifications")
    public ResponseEntity<Page<StudentNotificationModel>> getStudentNotifications(@RequestParam(value = "studentId") Long studentId,
                                                                                  @RequestParam(value = "pageSize") final int pageSize,
                                                                                  @RequestParam(value = "pageNumber") final int pageNumber,
                                                                                  @RequestParam(value = "columnName") final String columnName,
                                                                                  @RequestParam(value = "direction") final String direction){
        return ResponseEntity.ok(studentService.getStudentNotifications(studentId, pageSize, pageNumber, columnName, direction));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @PostMapping("/notification/seen")
    public ResponseEntity<Response> markNotificationAsSeen(@RequestParam(value="notificationId") Long notificationId){
        return ResponseEntity.ok(studentService.markNotificationAsSeen(notificationId));
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @GetMapping("/notifications/unseen")
    public int getUnseenNotifications(@RequestParam(value = "userId") final Long userId){
        return studentService.getUnseenNotifications(userId);
    }

    @PreAuthorize("hasAuthority('STUDENT')")
    @DeleteMapping("/document/own/delete/{id}")
    public ResponseEntity<Response> deleteOwnDocument(@PathVariable final Long id){
        return ResponseEntity.ok(studentService.deleteOwnDocument(id));
    }

}
