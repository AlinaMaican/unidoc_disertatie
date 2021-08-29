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
    private final Long documentId;
    private final String studyGroup;
    private final Long studyGroupId;
    private final String name;
    private final String description;
    private final String filePath;
    private final LocalDateTime dateOfUpload;
    private final String status;
    private final Long notificationId;
    private final String responseDocumentFilePath;
}
