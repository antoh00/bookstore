package me.eltacshikhsaidov.userloginregistration.service;

import lombok.AllArgsConstructor;
import me.eltacshikhsaidov.userloginregistration.entity.User;
import me.eltacshikhsaidov.userloginregistration.entity.role.UserRole;
import me.eltacshikhsaidov.userloginregistration.service.sender.EmailSender;
import me.eltacshikhsaidov.userloginregistration.service.token.ConfirmationToken;
import me.eltacshikhsaidov.userloginregistration.service.validator.EmailValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    public String register(RegistrationRequest request, UserRole userRole) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        String token = userService.signUpUser(
                new User(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        userRole
                )
        );

        String link = "http://localhost:8080/api/register/confirm?token=" + token;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getFirstName(), link));

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableAppUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    private String buildEmail(String name, String link) {

        return "<p>Hi " 
                    + name + ". Please confirm your email with <a href=" 
                    + link + " taget=\"_blank\">link</a>.It will expire after 15 minutes. Hurry up!</p>";
    }
}