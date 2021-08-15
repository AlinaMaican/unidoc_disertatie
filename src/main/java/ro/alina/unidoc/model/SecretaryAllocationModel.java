package ro.alina.unidoc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class SecretaryAllocationModel {
    private final Long allocationId;
    private final String learningType;
    private final String universityStudyType;
    private final String domain;
    private final String studyProgram;
    private final String studyYear;
}
