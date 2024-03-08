package com.example.demo.authService.auth;

import com.example.demo.authService.token.TokenRepository;
import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.Utente;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final PasswordEncoder passwordEncoder;
    private final UtentiRepository userRepository;
    private final TokenRepository tokenRepository;
    public boolean changePassword(ChangePasswordRequest request, String token) {
        var user = (Utente) tokenRepository.findByToken(token).get().utente;

        // Aggiornamento Password
        user.setPassword(passwordEncoder.encode(request.getNewpassword()));

        // Salvataggio
        userRepository.save(user);

        return true;
    }
}
