package ro.alina.unidoc.model.filters;

import lombok.*;

@Builder
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class StudentDocumentFilter {
    private String firstName;
    private String lastName;
    private String name;
    private String status;
    private Long studyGroupId;
    private Long studyYearId;
    private Long studyProgramId;
    private Long domainId;
    private Long universityStudyId;
    private Long learningTypeId;
    private Long secretaryId;
    private Long allocationId;
    private String columnName;
    private int pageSize;
    private int pageNumber;
    private String sortDirection;
}
