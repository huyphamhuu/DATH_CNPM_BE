package DATH_CNPM.WEB.backend.services;

import DATH_CNPM.WEB.backend.models.User;
import DATH_CNPM.WEB.backend.models.Token;
import DATH_CNPM.WEB.backend.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import DATH_CNPM.WEB.backend.repositories.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TokenRepo tokenRepo;


    public String register(User user) {
        userRepo.save(user);
        return "User registered successfully!";
    }

    public String authenticate(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(password)) {
                String token = UUID.randomUUID().toString();
                Token userToken = new Token(null, token, LocalDateTime.now(), LocalDateTime.now().plusDays(1), null, user);
                tokenRepo.save(userToken);
                return token;
            }
        }
        return "Invalid username or password!";
    }

    public boolean validateToken(String token) {
        Optional<Token> tokenOptional = tokenRepo.findByToken(token);
        return tokenOptional.isPresent() && tokenOptional.get().getExpiresAt().isAfter(LocalDateTime.now());
    }
}




