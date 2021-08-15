package ro.alina.unidoc.model.filters;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDocumentFilter {
    private String firstName;
    private String lastName;
    private String fileName;
    private String status;
    private Long studyGroupId;
    private Long studyYearId;
    private Long studyProgramId;
    private Long domainId;
    private Long universityStudyId;
    private Long learningTypeId;
    private Long secretaryAllocationId;
}
