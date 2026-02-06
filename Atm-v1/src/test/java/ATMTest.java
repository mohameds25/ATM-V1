import entities.ATM;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ATMTest {

    @Test
    void atmHasEnoughCash() {
        Map<Integer, Integer> cash = new HashMap<>();
        cash.put(50, 2); // total = 100

        ATM atm = new ATM(cash, 50, true);

        assertTrue(atm.hasEnoughCash(100));
    }
}