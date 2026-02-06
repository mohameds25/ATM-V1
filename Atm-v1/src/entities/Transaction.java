package entities;

import java.time.LocalDateTime;

public class Transaction {

    private String username;
    private int amount;
    private TransactionType type;
    private boolean receiptPrinted;
    private LocalDateTime timestamp;

    public Transaction(
            String username,
            int amount,
            TransactionType type,
            boolean receiptPrinted,
            LocalDateTime timestamp
    ) {
        this.username = username;
        this.amount = amount;
        this.type = type;
        this.receiptPrinted = receiptPrinted;
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public int getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public boolean isReceiptPrinted() {
        return receiptPrinted;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}