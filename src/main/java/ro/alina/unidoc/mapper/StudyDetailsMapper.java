package ro.alina.unidoc.mapper;

import ro.alina.unidoc.entity.SecretaryAllocation;
import ro.alina.unidoc.model.StudyDetailsModel;

public class StudyDetailsMapper {

    public static StudyDetailsModel toStudyDetails(final SecretaryAllocation allocation){
        return StudyDetailsModel.builder()
                .learningType(allocation.getLearningType().getName().toString())
                .universityStudyType(allocation.getUniversityStudyType().getName().toString())
                .domain(allocation.getDomain().getName().toString())
                .studyProgram(allocation.getStudyProgram().getName())
                .studyYear(allocation.getStudyYear().getName())
                .build();
    }

}
