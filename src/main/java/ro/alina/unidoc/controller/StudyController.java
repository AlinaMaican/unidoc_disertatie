package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.alina.unidoc.model.StudyModel;
import ro.alina.unidoc.service.StudyService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/study")
public class StudyController {

    private final StudyService studyService;

    @GetMapping("/learningTypes")
    public ResponseEntity<List<StudyModel>> getAllLearningTypes(){
        return ResponseEntity.ok(studyService.getAllLearningTypes());
    }

    @GetMapping("/universityStudyTypes")
    public ResponseEntity<List<StudyModel>> getAllUniversityStudyTypes(@RequestParam(name = "learningTypeId") final Long learningTypeId){
        return ResponseEntity.ok(studyService.getAllUniversityStudyTypes(learningTypeId));
    }

    @GetMapping("/domains")
    public ResponseEntity<List<StudyModel>> getAllDomains(@RequestParam(name = "universityStudyId") final Long universityStudyId){
        return ResponseEntity.ok(studyService.getAllDomains(universityStudyId));
    }

    @GetMapping("/studyPrograms")
    public ResponseEntity<List<StudyModel>> getAllStudyPrograms(@RequestParam(name = "domainId") final Long domainId){
        return ResponseEntity.ok(studyService.getAllStudyPrograms(domainId));
    }

    @GetMapping("/studyYears")
    public ResponseEntity<List<StudyModel>> getAllStudyYears(@RequestParam(name = "studyProgramId") final Long studyProgramId){
        return ResponseEntity.ok(studyService.getAllStudyYears(studyProgramId));
    }

    @GetMapping("/studyGroups")
    public ResponseEntity<List<StudyModel>> getAllStudyGroups(@RequestParam(name = "studyYearId") final Long studyYearId){
        return ResponseEntity.ok(studyService.getAllStudyGroups(studyYearId));
    }
}
