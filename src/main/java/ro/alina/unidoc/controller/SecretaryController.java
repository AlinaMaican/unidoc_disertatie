package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.alina.unidoc.model.*;
import ro.alina.unidoc.model.filters.StudentDocumentFilter;
import ro.alina.unidoc.model.property_editor.GenericPropertyEditor;
import ro.alina.unidoc.model.type.RoleType;
import ro.alina.unidoc.service.SecretaryService;

import javax.annotation.security.RolesAllowed;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/secretary")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SecretaryController {

    private final SecretaryService secretaryService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(SecretaryDocumentModel.class, new GenericPropertyEditor<>(SecretaryDocumentModel.class));
        binder.registerCustomEditor(StudentDocumentRowModel.class, new GenericPropertyEditor<>(StudentDocumentRowModel.class));
    }

    /**
     * gets all the secretaries for the admin view called Secretary Management
     *
     * @return returns a list of secretaries
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<SecretaryModel>> getAllSecretaries() {
        return ResponseEntity.ok(secretaryService.getAllSecretaries());
    }

    /**
     * gets all the allocations of a secretary
     *
     * @param id the id of the secretary
     * @return returns a list of the allocations
     */
    @PreAuthorize("hasAuthority('SECRETARY')")
    @GetMapping("/{id}/allocations")
    public ResponseEntity<List<SecretaryAllocationModel>> getSecretaryAllocationsBySecretaryId(@PathVariable Long id) {
        return ResponseEntity.ok(secretaryService.getSecretaryAllocationsBySecretaryId(id));
    }

    /**
     * get all the documents from a secretary allocation
     *
     * @param id the id of the allocation
     * @return a list of all the documents a secretary has uploaded for an allocation
     */
    @PreAuthorize("hasAuthority('SECRETARY')")
    @GetMapping("/allocation/{id}/documents")
    public ResponseEntity<List<SecretaryDocumentModel>> getSecretaryDocumentsByAllocation(@PathVariable Long id) {
        return ResponseEntity.ok(secretaryService.getSecretaryDocumentsByAllocationId(id));
    }

    /**
     * edits the details of a secretary document
     *
     * @return if the edit was a success then true, otherwise false
     */
    @PreAuthorize("hasAuthority('SECRETARY')")
    @PostMapping("/document/edit")
    public ResponseEntity<Boolean> editSecretaryDocument(@RequestParam(value = "id") String id,
                                                         @RequestParam(value = "name") String name,
                                                         @RequestParam(value = "description") String description,
                                                         @RequestParam(value = "endDateOfUpload")String endDateOfUpload) {
        return ResponseEntity.ok(secretaryService.editSecretaryDocument(SecretaryDocumentModel.builder()
                .id(Long.valueOf(id))
                .endDateOfUpload(LocalDateTime.parse(endDateOfUpload, DateTimeFormatter.ISO_DATE_TIME))
                .name(name)
                .description(description)
                .build()));
    }

    /**
     * uploads a secretary document for a specific allocation
     *
     * @param file                   the document
     * @param secretaryAllocationId  the secretaryAllocation id
     * @return a success or error message
     */
    @PreAuthorize("hasAuthority('SECRETARY')")
    @PostMapping("/document/upload")
    public ResponseEntity<Response> uploadSecretaryDocument(@RequestPart(value = "file") MultipartFile file,
                                                            @RequestParam(value = "allocationId") String secretaryAllocationId,
                                                            @RequestParam(value = "name") String name,
                                                            @RequestParam(value = "description") String description,
                                                            @RequestParam(value = "endDateOfUpload")String endDateOfUpload) throws FileAlreadyExistsException {
       return ResponseEntity.ok(secretaryService.uploadSecretaryDocument(file, SecretaryDocumentModel.builder()
                .endDateOfUpload(LocalDateTime.parse(endDateOfUpload, DateTimeFormatter.ISO_DATE_TIME))
                .name(name)
                .description(description)
                .build(), Long.valueOf(secretaryAllocationId)));
    }

    /**
     * gets all of the documents of the students filtered by some conditions
     *
     * @return a list of student documents along with some student details
     */
    @PreAuthorize("hasAuthority('SECRETARY')")
    @ModelAttribute
    @GetMapping("/allocation/student/documents")
    public ResponseEntity<Page<StudentDocumentRowModel>> getAllStudentDocuments(@ModelAttribute final StudentDocumentFilter filter) {
        return ResponseEntity.ok(secretaryService.getAllStudentDocuments(filter));
    }

    /**
     * edits the status of a student document
     *
     * @param id     the id of the document
     * @param status the new status
     * @return true or false depending on the success of the edit
     */
    @PreAuthorize("hasAuthority('SECRETARY')")
    @PostMapping("/allocation/student/document/{id}")
    ResponseEntity<Response> editStudentDocumentStatus(@PathVariable final Long id,
                                                      @RequestParam(value = "status") final String status,
                                                      @RequestParam(value = "comment", required = false) String comment) {
        return ResponseEntity.ok(secretaryService.editStudentDocumentStatus(id, status, comment));
    }

    @PreAuthorize("hasAuthority('SECRETARY') or hasAuthority('STUDENT')")
    @RequestMapping(value = "/downloadPdfDocument", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<byte[]> getPDF(@RequestParam(value = "filePath") String filePath) {
        FileInputStream fileStream;
        try {
            fileStream = new FileInputStream(new File (filePath));
            byte[] contents = IOUtils.toByteArray(fileStream);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/pdf"));
            String filename = "test.pdf";
            headers.setContentDispositionFormData(filename, filename);
            return new ResponseEntity<>(contents, headers, HttpStatus.OK);
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    @PreAuthorize("hasAuthority('SECRETARY')")
    @GetMapping("/notifications/unseen")
    public int getUnseenNotifications(@RequestParam(value = "userId") final Long userId){
        return secretaryService.getUnseenNotifications(userId);
    }

    @PreAuthorize("hasAuthority('SECRETARY')")
    @PostMapping("/notification/seen")
    public ResponseEntity<Response> markNotificationAsSeen(@RequestParam(value="notificationId") Long notificationId){
        return ResponseEntity.ok(secretaryService.markNotificationAsSeen(notificationId));
    }

    @PreAuthorize("hasAuthority('SECRETARY')")
    @ModelAttribute
    @GetMapping("/allocation/student/own/documents")
    public ResponseEntity<Page<StudentDocumentRowModel>> getOwnStudentDocuments(@ModelAttribute final StudentDocumentFilter filter) {
        return ResponseEntity.ok(secretaryService.getOwnStudentDocuments(filter));
    }
}
