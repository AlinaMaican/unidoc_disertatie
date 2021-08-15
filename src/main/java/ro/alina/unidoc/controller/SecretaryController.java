package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.alina.unidoc.model.SecretaryAllocationModel;
import ro.alina.unidoc.model.SecretaryDocumentModel;
import ro.alina.unidoc.model.SecretaryModel;
import ro.alina.unidoc.model.StudentDocumentRowModel;
import ro.alina.unidoc.model.filters.StudentDocumentFilter;
import ro.alina.unidoc.model.property_editor.GenericPropertyEditor;
import ro.alina.unidoc.service.SecretaryService;

import java.util.List;
import java.util.Optional;


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
    }

    /**
     * gets all the secretaries for the admin view called Secretary Management
     *
     * @return returns a list of secretaries
     */
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
    @GetMapping("/allocation/{id}/documents")
    public ResponseEntity<List<SecretaryDocumentModel>> getSecretaryDocumentsByAllocation(@PathVariable Long id) {
        return ResponseEntity.ok(secretaryService.getSecretaryDocumentsByAllocationId(id));
    }

    /**
     * edits the details of a secretary document
     *
     * @param secretaryDocumentModel the secretary document details object
     * @return if the edit was a success then true, otherwise false
     */
    @PatchMapping("/document/edit")
    public ResponseEntity<Boolean> editSecretaryDocument(@RequestBody SecretaryDocumentModel secretaryDocumentModel) {
        return ResponseEntity.ok(secretaryService.editSecretaryDocument(secretaryDocumentModel));
    }

    /**
     * uploads a secretary document for a specific allocation
     *
     * @param file                   the document
     * @param secretaryAllocationId  the secretaryAllocation id
     * @param secretaryDocumentModel the details of the document
     * @return a success or error message
     */
    @PostMapping("/document/upload")
    public ResponseEntity<String> uploadSecretaryDocument(@RequestParam("file") MultipartFile file,
                                                          @RequestParam("secretaryAllocationId") Long secretaryAllocationId,
                                                          @RequestBody SecretaryDocumentModel secretaryDocumentModel) {
        return ResponseEntity.ok(secretaryService.uploadSecretaryDocument(file, secretaryDocumentModel, secretaryAllocationId));
    }

    /**
     * gets all of the documents of the students filtered by some conditions
     *
     * @param pageable the pageable object
     * @param filter   filter object
     * @return a list of student documents alng with some student details
     */
    @GetMapping("/allocation/student/documents")
    public ResponseEntity<Page<StudentDocumentRowModel>> getAllStudentDocuments(final Pageable pageable,
                                                                                @RequestParam(name = "filter", required = false) final StudentDocumentFilter filter) {
        return ResponseEntity.ok(secretaryService.getAllStudentDocuments(pageable, Optional.ofNullable(filter)
                .orElseGet(StudentDocumentFilter::new)));
    }

    /**
     * edits the status of a student document
     *
     * @param id     the id of the document
     * @param status the new status
     * @return true or false depending on the success of the edit
     */
    @PatchMapping("/allocation/student/document/{id}")
    ResponseEntity<Boolean> editStudentDocumentStatus(@PathVariable final Long id,
                                                      @RequestParam(value = "status") final String status) {
        return ResponseEntity.ok(secretaryService.editStudentDocumentStatus(id, status));
    }
}
