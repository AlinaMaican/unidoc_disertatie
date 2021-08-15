package ro.alina.unidoc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
public class StudentDocumentRowModel {
    private final StudentModel studentModel;
    private final String studyGroup;
    private final Long studyGroupId;
    private final String fileName;
    private final String filePath;
    private final LocalDateTime dateOfUpload;
    private final String status;
}
