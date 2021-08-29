package ro.alina.unidoc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
public class SecretaryDocumentModel {
    private final MultipartFile file;
    private final Long id;
    private final String name;
    private final String description;
    private final String filePathName;
    private final LocalDateTime endDateOfUpload;
    private final Long secretaryId;
    private final Long allocationId;
    private final Long studentDocumentId;
    private final String learningType;
    private final String universityStudy;
    private final String domain;
    private final String studyProgram;
    private final String studyYear;
}
