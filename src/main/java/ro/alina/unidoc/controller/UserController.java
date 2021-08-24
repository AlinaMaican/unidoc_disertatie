package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.alina.unidoc.model.StudentModel;
import ro.alina.unidoc.repository.SecretaryRepository;
import ro.alina.unidoc.repository.StudentRepository;
import ro.alina.unidoc.service.StudentService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private final StudentRepository studentRepository;
    private final SecretaryRepository secretaryRepository;
    private final StudentService studentService;

    @GetMapping("/student")
    public ResponseEntity<?> getStudentByUser(@RequestParam(value="userId") Long userId){
        return ResponseEntity.ok(studentRepository.findByUserId(userId));
    }

    @GetMapping("/secretary")
    public ResponseEntity<?> getSecretaryByUser(@RequestParam(value="userId") Long userId){
        return ResponseEntity.ok(secretaryRepository.findByUserId(userId));
    }

    @GetMapping("/user-profile")
    public ResponseEntity<StudentModel> getUserProfile(@RequestParam(value="userId") Long userId){
        return ResponseEntity.ok(studentService.getUserProfile(userId));
    }
}
