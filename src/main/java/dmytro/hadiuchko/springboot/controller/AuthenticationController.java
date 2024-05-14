package dmytro.hadiuchko.springboot.controller;

import dmytro.hadiuchko.springboot.dto.user.request.UserLoginRequestDto;
import dmytro.hadiuchko.springboot.dto.user.request.UserRegistrationRequestDto;
import dmytro.hadiuchko.springboot.dto.user.response.UserLoginResponseDto;
import dmytro.hadiuchko.springboot.dto.user.response.UserResponseDto;
import dmytro.hadiuchko.springboot.exception.RegistrationException;
import dmytro.hadiuchko.springboot.security.AuthenticationService;
import dmytro.hadiuchko.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register new user")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto requestDto) {
        return authenticationService.authenticate(requestDto);
    }
}
