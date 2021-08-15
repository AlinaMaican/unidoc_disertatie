package ro.alina.unidoc.mapper;

import ro.alina.unidoc.entity.SecretaryAllocation;
import ro.alina.unidoc.model.SecretaryAllocationModel;

public class SecretaryAllocationMapper {
    public static SecretaryAllocationModel toSecretaryAllocationModel(final SecretaryAllocation allocation) {
        return SecretaryAllocationModel.builder()
                .allocationId(allocation.getId())
                .learningType(allocation.getLearningType().getName().toString())
                .universityStudyType(allocation.getUniversityStudyType().getName().toString())
                .domain(allocation.getDomain().getName().toString())
                .studyProgram(allocation.getStudyProgram().getName())
                .studyYear(allocation.getStudyYear().getName().toString())
                .build();

    }
}
