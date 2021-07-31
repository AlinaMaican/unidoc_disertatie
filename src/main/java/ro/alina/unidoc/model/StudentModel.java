package ro.alina.unidoc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Data
@Getter
@Setter
public class StudentModel {
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String emailAddress;
    private final List<String> phoneNumbers;
    private final String cnp;
    private final String registrationNumber;
}
