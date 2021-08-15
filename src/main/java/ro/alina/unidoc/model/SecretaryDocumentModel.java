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
public class SecretaryDocumentModel {
    private final Long id;
    private final String name;
    private final String description;
    private final String filePathName;
    private final LocalDateTime endDateOfUpload;
}
