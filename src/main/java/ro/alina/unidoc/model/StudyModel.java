package ro.alina.unidoc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class StudyModel {
    private Long id;
    private String value;
    private String name;
}
