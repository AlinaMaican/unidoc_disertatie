package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ro.alina.unidoc.model.*;
import ro.alina.unidoc.model.property_editor.GenericPropertyEditor;
import ro.alina.unidoc.service.StudyService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudyController {

    private final StudyService studyService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(AllocationModel.class, new GenericPropertyEditor<>(AllocationModel.class));
    }

    /**
     * gets all the learning types
     *
     * @return list of learning types
     */
    @GetMapping("/learningTypes")
    public ResponseEntity<List<StudyModel>> getAllLearningTypes() {
        return ResponseEntity.ok(studyService.getAllLearningTypes());
    }

    /**
     * gets all the learning types that a secretary can see
     *
     * @param secretaryId the id of the secretary
     * @return list of learning types
     */
    @GetMapping("/filteredLearningTypes")
    public ResponseEntity<List<StudyModel>> getAllFilteredLearningTypes(@RequestParam(value = "secretaryId") final Long secretaryId) {
        return ResponseEntity.ok(studyService.getAllFilteredLearningTypes(secretaryId));
    }

    /**
     * gets all the study types of a specific learning type
     *
     * @param learningTypeId the learning type id
     * @return list of study types
     */
    @GetMapping("/universityStudyTypes")
    public ResponseEntity<List<StudyModel>> getAllUniversityStudyTypes(@RequestParam(name = "learningTypeId") final Long learningTypeId) {
        return ResponseEntity.ok(studyService.getAllUniversityStudyTypes(learningTypeId));
    }

    /**
     * gets all the study types of a specific learning type that a secretary can see
     *
     * @param learningTypeId the learning type id
     * @param secretaryId    the id of the secretary
     * @return list of study types
     */
    @GetMapping("/filteredUniversityStudyTypes")
    public ResponseEntity<List<StudyModel>> getAllFilteredUniversityStudyTypes(@RequestParam(name = "learningTypeId") final Long learningTypeId,
                                                                               @RequestParam(value = "secretaryId") final Long secretaryId) {
        return ResponseEntity.ok(studyService.getAllFilteredUniversityStudyTypes(learningTypeId, secretaryId));
    }

    /**
     * gets all the domains of a specific university study type
     *
     * @param universityStudyId the university study id
     * @return a list of domains
     */
    @GetMapping("/domains")
    public ResponseEntity<List<StudyModel>> getAllDomains(@RequestParam(name = "universityStudyId") final Long universityStudyId) {
        return ResponseEntity.ok(studyService.getAllDomains(universityStudyId));
    }

    /**
     * gets all the domains of a specific university study type that a secretary can see
     *
     * @param universityStudyId the university study id
     * @param secretaryId       the id of the secretary
     * @return a list of domains
     */
    @GetMapping("/filteredDomains")
    public ResponseEntity<List<StudyModel>> getAllFilteredDomains(@RequestParam(name = "universityStudyId") final Long universityStudyId,
                                                                  @RequestParam(value = "secretaryId") final Long secretaryId) {
        return ResponseEntity.ok(studyService.getAllFilteredDomains(universityStudyId, secretaryId));
    }

    /**
     * gets all the study programs of a specific domain
     *
     * @param domainId the domain id
     * @return a list of study programs
     */
    @GetMapping("/studyPrograms")
    public ResponseEntity<List<StudyModel>> getAllStudyPrograms(@RequestParam(name = "domainId") final Long domainId) {
        return ResponseEntity.ok(studyService.getAllStudyPrograms(domainId));
    }

    /**
     * gets all the study programs of a specific domain that a secretary can see
     *
     * @param domainId    the domain id
     * @param secretaryId the id of the secretary
     * @return a list of study programs
     */
    @GetMapping("/filteredStudyPrograms")
    public ResponseEntity<List<StudyModel>> getAllFilteredStudyPrograms(@RequestParam(name = "domainId") final Long domainId,
                                                                        @RequestParam(value = "secretaryId") final Long secretaryId) {
        return ResponseEntity.ok(studyService.getAllFilteredStudyPrograms(domainId, secretaryId));
    }

    /**
     * gets all the study years of a specific study program
     *
     * @param studyProgramId the study program id
     * @return a list of study years
     */
    @GetMapping("/studyYears")
    public ResponseEntity<List<StudyModel>> getAllStudyYears(@RequestParam(name = "studyProgramId") final Long studyProgramId) {
        return ResponseEntity.ok(studyService.getAllStudyYears(studyProgramId));
    }

    /**
     * gets all the study years of a specific study program that a secretary can see
     *
     * @param studyProgramId the study program id
     * @param secretaryId    the id of the secretary
     * @return a list of study years
     */
    @GetMapping("/filteredStudyYears")
    public ResponseEntity<List<StudyModel>> getAllFilteredStudyYears(@RequestParam(name = "studyProgramId") final Long studyProgramId,
                                                                     @RequestParam(value = "secretaryId") final Long secretaryId) {
        return ResponseEntity.ok(studyService.getAllFilteredStudyYears(studyProgramId, secretaryId));
    }

    /**
     * gets all the study groups of a specific study year
     *
     * @return a list of study groups
     */
    @GetMapping("/studyGroups")
    public ResponseEntity<List<StudyModel>> getAllStudyGroups(@RequestParam(name = "allocationId") final Long allocationId) {
        return ResponseEntity.ok(studyService.getAllStudyGroups(allocationId));
    }

    @PostMapping("/allocation/create")
    public ResponseEntity<Response> createAllocation(@RequestBody AllocationModel allocationModel) {
        return ResponseEntity.ok(studyService.createAllocation(allocationModel));
    }

    @DeleteMapping("/allocation/delete/{allocationId}")
    public ResponseEntity<Response> deleteAllocation(@PathVariable Long allocationId) {
        return ResponseEntity.ok(studyService.deleteAllocation(allocationId));
    }

    @GetMapping("/allocationFilter")
    public ResponseEntity<List<StudyModel>> getAllocationFilter(@RequestParam(name = "secretaryId") final Long secretaryId) {
        return ResponseEntity.ok(studyService.getAllocationFilter(secretaryId));
    }

    @GetMapping("/allowedLearningTypes")
    public ResponseEntity<List<StudyModel>> getAllowedLearningTypes(@RequestParam(value = "secretaryId") final Long secretaryId) {
        return ResponseEntity.ok(studyService.getAllowedLearningTypes(secretaryId));
    }

    @GetMapping("/allowedUniversityStudies")
    public ResponseEntity<List<StudyModel>> getAllowedUniversityStudies(@RequestParam(value = "secretaryId") final Long secretaryId,
                                                                        @RequestParam(value = "learningType") final String learningType) {
        return ResponseEntity.ok(studyService.getAllowedUniversityStudies(secretaryId, learningType));
    }

    @GetMapping("/allowedDomains")
    public ResponseEntity<List<StudyModel>> getAllowedDomains(@RequestParam(value = "secretaryId") final Long secretaryId,
                                                              @RequestParam(value = "learningType") final String learningType,
                                                              @RequestParam(value = "universityStudy") final String universityStudy) {
        return ResponseEntity.ok(studyService.getAllowedDomains(secretaryId, learningType, universityStudy));
    }

    @GetMapping("/allowedStudyPrograms")
    public ResponseEntity<List<StudyModel>> getAllowedStudyPrograms(@RequestParam(value = "secretaryId") final Long secretaryId,
                                                                    @RequestParam(value = "learningType") final String learningType,
                                                                    @RequestParam(value = "universityStudy") final String universityStudy,
                                                                    @RequestParam(value = "domain") final String domain) {
        return ResponseEntity.ok(studyService.getAllowedStudyPrograms(secretaryId, learningType, universityStudy, domain));
    }

    @GetMapping("/allowedStudyYears")
    public ResponseEntity<List<StudyModel>> getAllowedStudyYears(@RequestParam(value = "secretaryId") final Long secretaryId,
                                                                 @RequestParam(value = "learningType") final String learningType,
                                                                 @RequestParam(value = "universityStudy") final String universityStudy,
                                                                 @RequestParam(value = "domain") final String domain,
                                                                 @RequestParam(value = "studyProgram") final String studyProgram) {
        return ResponseEntity.ok(studyService.getAllowedStudyYears(secretaryId, learningType, universityStudy, domain, studyProgram));
    }
}
