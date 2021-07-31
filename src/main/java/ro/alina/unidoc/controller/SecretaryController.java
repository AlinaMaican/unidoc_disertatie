package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.alina.unidoc.model.SecretaryModel;
import ro.alina.unidoc.service.SecretaryService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/secretary")
public class SecretaryController {

    private final SecretaryService secretaryService;

    @GetMapping("/all")
    public ResponseEntity<List<SecretaryModel>> getAllSecretaries(){
        return ResponseEntity.ok(secretaryService.getAllSecretaries());
    }
}
