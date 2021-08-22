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
public class StudentDocumentModel {
    private final String name;
    private final String description;
    private final String status;
    private final LocalDateTime dateOfUpload;
    private final String filePathName;
}
