import entities.ATM;
import repositories.ATMRepository;

public class InMemoryATMRepository implements ATMRepository {

    private ATM atm;

    @Override
    public ATM loadATM() {
        return atm;
    }

    @Override
    public void saveATM(ATM atm) {
        this.atm = atm;
    }
}