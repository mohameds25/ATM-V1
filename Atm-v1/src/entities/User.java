package entities;

public class User {

    private String username;
    private String pin;
    private double balance;

    // 🔐 Security fields
    private int failedAttempts;
    private boolean locked;

    // =====================
    // Constructor (FULL)
    // =====================
    public User(String username, String pin, double balance,
                int failedAttempts, boolean locked) {

        this.username = username;
        this.pin = pin;
        this.balance = balance;
        this.failedAttempts = failedAttempts;
        this.locked = locked;
    }

    // =====================
    // Getters & Setters
    // =====================
    public String getUsername() {
        return username;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

    public void setFailedAttempts(int failedAttempts) {
        this.failedAttempts = failedAttempts;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}