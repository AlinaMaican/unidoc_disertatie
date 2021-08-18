package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.alina.unidoc.model.StudyModel;
import ro.alina.unidoc.service.StudyService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudyController {

    private final StudyService studyService;

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
     * @param studyYearId the study year id
     * @return a list of study groups
     */
    @GetMapping("/studyGroups")
    public ResponseEntity<List<StudyModel>> getAllStudyGroups(@RequestParam(name = "studyYearId") final Long studyYearId) {
        return ResponseEntity.ok(studyService.getAllStudyGroups(studyYearId));
    }
}
