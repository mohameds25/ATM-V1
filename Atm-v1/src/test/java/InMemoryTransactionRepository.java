import entities.Transaction;
import repositories.TransactionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions = new ArrayList<>();

    @Override
    public void save(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> findByUsername(String username) {
        return transactions.stream()
                .filter(t -> t.getUsername().equals(username))
                .collect(Collectors.toList());
    }
}