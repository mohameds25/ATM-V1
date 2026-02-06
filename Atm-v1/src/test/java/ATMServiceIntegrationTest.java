import entities.ATM;
import entities.User;
import entities.TransactionType;
import org.junit.jupiter.api.Test;
import repositories.ATMRepository;
import repositories.TransactionRepository;
import repositories.UserRepository;
import services.ATMService;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ATMServiceIntegrationTest {

    @Test
    void withdrawUpdatesUserBalanceAndCreatesTransaction() {

        // Arrange (real in-memory repositories)
        UserRepository userRepository = new InMemoryUserRepository();
        TransactionRepository transactionRepository = new InMemoryTransactionRepository();
        ATMRepository atmRepository = new InMemoryATMRepository();

        ATMService atmService =
                new ATMService(userRepository, atmRepository, transactionRepository);

        // Create user
        User user = new User("ahmed", "1234", 200.0, 0, false);
        userRepository.update(user);

        // Create ATM
        Map<Integer, Integer> cash = new HashMap<>();
        cash.put(50, 10); // total = 500
        ATM atm = new ATM(cash, 50, true);
        atmRepository.saveATM(atm);

        // Act
        boolean result = atmService.withdraw(user, 100, true);

        // Assert
        assertTrue(result);
        assertEquals(100.0,
                userRepository.findByUsername("ahmed").getBalance());

        assertEquals(1,
                transactionRepository.findByUsername("ahmed").size());

        assertEquals(TransactionType.WITHDRAW,
                transactionRepository.findByUsername("ahmed").get(0).getType());
    }
}