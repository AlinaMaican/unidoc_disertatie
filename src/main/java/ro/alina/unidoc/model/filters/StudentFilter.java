package ro.alina.unidoc.model.filters;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentFilter {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private Long studyGroupId;
    private Long studyYearId;
    private Long studyProgramId;
    private Long domainId;
    private Long universityStudyId;
    private Long learningTypeId;
    private Long secretaryAllocationId;
}
