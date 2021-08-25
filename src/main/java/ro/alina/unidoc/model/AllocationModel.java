package ro.alina.unidoc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
@Getter
@Setter
public class AllocationModel {
    private final Long secretaryId;
    private final Long allocationSecretaryId;
    private final Long learningTypeId;
    private final Long universityStudyTypeId;
    private final Long domainId;
    private final Long studyProgramId;
    private final Long studyYearId;
}
