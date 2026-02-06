package services;

import entities.User;
import repositories.UserRepository;

public class AuthenticationService {

    private final UserRepository userRepository;
    private static final int MAX_ATTEMPTS = 3;

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String pin) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("❌ User not found");
            return null;
        }

        // 🔒 Check locked account
        if (user.isLocked()) {
            System.out.println("🔒 Account is locked. Please contact the bank.");
            return null;
        }

        // ✅ Correct PIN
        if (user.getPin().equals(pin)) {
            user.setFailedAttempts(0); // reset attempts
            userRepository.update(user);
            return user;
        }

        // ❌ Wrong PIN
        int attempts = user.getFailedAttempts() + 1;
        user.setFailedAttempts(attempts);

        if (attempts >= MAX_ATTEMPTS) {
            user.setLocked(true);
            System.out.println("🔒 Account locked after 3 failed attempts.");
        } else {
            System.out.println(
                    "❌ Wrong PIN. Attempts left: " + (MAX_ATTEMPTS - attempts)
            );
        }

        userRepository.update(user);
        return null;
    }
}