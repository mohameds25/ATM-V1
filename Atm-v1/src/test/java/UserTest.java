import entities.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void userBalanceUpdatesCorrectly() {

        // Arrange
        User user = new User(
                "ahmed",
                "1234",
                100.0,
                0,
                false
        );

        // Act
        user.setBalance(user.getBalance() + 50);

        // Assert
        assertEquals(150.0, user.getBalance());
    }
}