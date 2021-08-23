package ro.alina.unidoc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentNotificationModel {
    private Long id;
    private String documentName;
    private LocalDateTime date;
    private String message;
    private Long studentDocumentId;
    private Boolean seen;
}
