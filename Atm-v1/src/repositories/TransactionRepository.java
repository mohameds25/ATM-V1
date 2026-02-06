package repositories;

import entities.Transaction;
import java.util.List;

public interface TransactionRepository {

    void save(Transaction transaction);

    List<Transaction> findByUsername(String username);
}