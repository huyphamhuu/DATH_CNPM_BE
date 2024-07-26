package DATH_CNPM.WEB.backend.controllers;

import DATH_CNPM.WEB.backend.models.User;
import DATH_CNPM.WEB.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import DATH_CNPM.WEB.backend.services.AuthenticationRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/login")
    public String login(@RequestBody @Valid AuthenticationRequest request) {
        return userService.authenticate(request.getEmail(), request.getPassword());
    }

    @GetMapping("/validate")
    public boolean validateToken(@RequestParam String token) {
        return userService.validateToken(token);
    }
}
