package ro.alina.unidoc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Setter
@Getter
public class Response {
    private String message;
    private String type;
}
