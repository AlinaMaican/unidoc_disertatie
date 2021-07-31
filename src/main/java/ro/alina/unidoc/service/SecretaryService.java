package ro.alina.unidoc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.alina.unidoc.entity.PhoneNumber;
import ro.alina.unidoc.mapper.StudyDetailsMapper;
import ro.alina.unidoc.model.SecretaryModel;
import ro.alina.unidoc.repository.PhoneNumberRepository;
import ro.alina.unidoc.repository.SecretaryAllocationRepository;
import ro.alina.unidoc.repository.SecretaryRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class SecretaryService {
    private final SecretaryRepository secretaryRepository;
    private final SecretaryAllocationRepository secretaryAllocationRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    public List<SecretaryModel> getAllSecretaries() {
        var secretaries = secretaryRepository.findAll();
        List<SecretaryModel> secretaryModels = new ArrayList<>();

        secretaries.forEach(s -> {
            var studyDetails = secretaryAllocationRepository.findAllBySecretary_Id(s.getId())
                    .stream()
                    .map(StudyDetailsMapper::toStudyDetails)
                    .collect(Collectors.toList());
            secretaryModels.add(SecretaryModel.builder()
                    .id(s.getId())
                    .firstName(s.getFirstName())
                    .lastName(s.getLastName())
                    .emailAddress(s.getUser().getEmail())
                    .phoneNumbers(phoneNumberRepository.findAllByUser(s.getUser())
                            .stream()
                            .map(PhoneNumber::getPhoneNumber)
                            .collect(Collectors.toList()))
                    .studyDetails(studyDetails)
                    .build());
        });
        return secretaryModels;
    }
}
