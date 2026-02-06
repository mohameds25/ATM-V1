package services;

import entities.User;
import entities.Transaction;
import entities.TransactionType;
import repositories.UserRepository;
import repositories.ATMRepository;
import repositories.TransactionRepository;

import java.time.LocalDateTime;

public class ATMService {

    private final UserRepository userRepository;
    private final ATMRepository atmRepository;
    private final TransactionRepository transactionRepository;

    public ATMService(
            UserRepository userRepository,
            ATMRepository atmRepository,
            TransactionRepository transactionRepository
    ) {
        this.userRepository = userRepository;
        this.atmRepository = atmRepository;
        this.transactionRepository = transactionRepository;
    }

    // ======================
    // DEPOSIT
    // ======================
    public boolean deposit(User user, double amount, boolean printReceipt) {

        if (amount <= 0) {
            System.out.println("❌ Invalid amount");
            return false;
        }

        user.setBalance(user.getBalance() + amount);
        userRepository.update(user);

        transactionRepository.save(
                new Transaction(
                        user.getUsername(),
                        (int) amount,
                        TransactionType.DEPOSIT,
                        printReceipt,
                        LocalDateTime.now()
                )
        );

        System.out.println("✅ Deposit successful");
        return true;
    }

    // ======================
    // WITHDRAW
    // ======================
    public boolean withdraw(User user, double amount, boolean printReceipt) {

        if (amount <= 0) {
            System.out.println("❌ Invalid amount");
            return false;
        }

        if (user.getBalance() < amount) {
            System.out.println("❌ Insufficient balance");
            return false;
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.update(user);

        transactionRepository.save(
                new Transaction(
                        user.getUsername(),
                        (int) amount,
                        TransactionType.WITHDRAW,
                        printReceipt,
                        LocalDateTime.now()
                )
        );

        System.out.println("💵 Cash withdrawn");
        return true;
    }

    // ======================
    // TRANSFER
    // ======================
    public boolean transfer(User sender, String receiverUsername, double amount) {

        if (amount <= 0) {
            System.out.println("❌ Invalid amount");
            return false;
        }

        if (sender.getBalance() < amount) {
            System.out.println("❌ Insufficient balance");
            return false;
        }

        User receiver = userRepository.findByUsername(receiverUsername);

        if (receiver == null) {
            System.out.println("❌ Receiver not found");
            return false;
        }

        if (receiver.getUsername().equals(sender.getUsername())) {
            System.out.println("❌ Cannot transfer to yourself");
            return false;
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.update(sender);
        userRepository.update(receiver);

        transactionRepository.save(
                new Transaction(
                        sender.getUsername(),
                        (int) amount,
                        TransactionType.TRANSFER,
                        false,
                        LocalDateTime.now()
                )
        );

        System.out.println("✅ Transfer successful");
        return true;
    }
}