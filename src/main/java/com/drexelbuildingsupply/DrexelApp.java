package com.drexelbuildingsupply;

import com.drexelbuildingsupply.model.User;
import com.drexelbuildingsupply.repository.UserRepository;
import com.drexelbuildingsupply.service.EmailService;

import java.util.UUID;

public class DrexelApp {
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        EmailService emailService = new EmailService();

        // Generate a unique verification code
        String verificationCode = UUID.randomUUID().toString();

        // Save a new user with the verification code
        User newUser = new User(null, "janedoe", "janedoe@example.com", "securepassword", false, verificationCode);
        userRepository.saveUser(newUser);

        System.out.println("User registered successfully!");
        System.out.println("Verification code for the user: " + verificationCode);

        // Send a verification email
        emailService.sendVerificationEmail(newUser.getEmail(), newUser.getUsername(), verificationCode);

        System.out.println("Verification email sent to: " + newUser.getEmail());
    }
}
