package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ro.alina.unidoc.entity.PhoneNumber;
import ro.alina.unidoc.entity.Student;
import ro.alina.unidoc.model.StudentModel;
import ro.alina.unidoc.model.filters.StudentFilter;
import ro.alina.unidoc.repository.PhoneNumberRepository;
import ro.alina.unidoc.repository.StudentRepository;
import ro.alina.unidoc.utils.GenericSpecification;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final GenericSpecification<Student> studentGenericSpecification;
    private final StudentRepository studentRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public Page<StudentModel> getAllStudents(final Pageable pageable, final StudentFilter filter) {
        final var specification = getSpecifications(filter);
        return studentRepository.findAll(specification, pageable).map(this::toStudentModel);
    }

    private Specification<Student> getSpecifications(final StudentFilter filter) {
        return Objects.requireNonNull(studentGenericSpecification.where(
                studentGenericSpecification.isPropertyLike("first_name", filter.getFirstName()))
                .and(studentGenericSpecification.isPropertyLike("last_name", filter.getLastName()))
                .and(studentGenericSpecification.isNestedPropertyLike("user.email", filter.getEmailAddress()))
                .and(studentGenericSpecification.isPropertyEqualNumber("secretary_allocation_id", filter.getSecretaryAllocationId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("learning_type_id", filter.getSecretaryAllocationId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("university_study_type_id", filter.getSecretaryAllocationId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("domain_id", filter.getSecretaryAllocationId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("study_program_id", filter.getSecretaryAllocationId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("study_year_id", filter.getSecretaryAllocationId()))
                .and(studentGenericSpecification.isPropertyEqualNumber("study_group_id", filter.getSecretaryAllocationId())));
    }

    private StudentModel toStudentModel(final Student student) {
        return StudentModel.builder()
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .emailAddress(student.getUser().getEmail())
                .cnp(student.getCnp())
                .registrationNumber(student.getRegistrationNumber())
                .phoneNumbers(phoneNumberRepository.findAllByUser(student.getUser())
                        .stream()
                        .map(PhoneNumber::getPhoneNumber)
                        .collect(Collectors.toList()))
                .build();
    }
}
