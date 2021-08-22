package ro.alina.unidoc.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequiredDocumentsRowModel {
    private Long secretaryDocumentId;
    private String secretaryDocumentName;
    private String secretaryDocumentPathName;
    private LocalDateTime endDateOfUpload;
    private StudentDocumentModel studentDocumentModel;

}
