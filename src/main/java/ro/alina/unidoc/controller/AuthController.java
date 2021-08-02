package ro.alina.unidoc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.alina.unidoc.entity.User;
import ro.alina.unidoc.model.JwtResponseModel;
import ro.alina.unidoc.model.UserChangePasswordModel;
import ro.alina.unidoc.model.UserLoginModel;
import ro.alina.unidoc.repository.UserRepository;
import ro.alina.unidoc.service.UserDetailsImpl;
import ro.alina.unidoc.service.UserService;
import ro.alina.unidoc.utils.JwtUtils;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginModel userLoginModel) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginModel.getEmail(), userLoginModel.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            JwtResponseModel res = JwtResponseModel.builder()
                    .token(jwt)
                    .type("Bearer")
                    .email(userDetails.getEmail())
                    .roles(roles)
                    .isActive(userDetails.isActive())
                    .build();
            return ResponseEntity.ok(res);
    }

    @PostMapping("/change_password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody UserChangePasswordModel userChangePasswordModel) {
        User user = userRepository.findByEmail(userChangePasswordModel.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " +
                        userChangePasswordModel.getEmail()));
        if (userChangePasswordModel.getPassword().equals(userChangePasswordModel.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(userChangePasswordModel.getPassword()));
            user.setIsActive(true);
            userRepository.save(user);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
    }
}
