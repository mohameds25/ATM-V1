import entities.ATM;
import entities.User;
import services.ATMService;
import services.AuthenticationService;
import services.TechnicianService;
import repositories.UserRepository;
import repositories.ATMRepository;
import repositories.TransactionRepository;
import repositories.json.JsonUserRepository;
import repositories.json.JsonATMRepository;
import repositories.json.JsonTransactionRepository;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final UserRepository userRepository = new JsonUserRepository();
    private static final ATMRepository atmRepository = new JsonATMRepository();
    private static final TransactionRepository transactionRepository = new JsonTransactionRepository();

    private static final AuthenticationService authenticationService =
            new AuthenticationService(userRepository);

    private static final ATMService atmService =
            new ATMService(userRepository, atmRepository, transactionRepository);

    public static void main(String[] args) {

        boolean running = true;

        while (running) {
            System.out.println("\n===== ATM SYSTEM =====");
            System.out.println("1. User Login");
            System.out.println("2. Technician");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> userLogin();
                case 2 -> technicianLogin();
                case 3 -> {
                    System.out.println("👋 Goodbye");
                    running = false;
                }
                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    // =========================
    // USER LOGIN
    // =========================
    private static void userLogin() {

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("PIN: ");
        String pin = scanner.nextLine();

        User user = authenticationService.authenticate(username, pin);

        if (user != null) {
            userMenu(user);
        }
    }

    // =========================
    // USER MENU
    // =========================
    private static void userMenu(User user) {

        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n===== USER MENU =====");
            System.out.println("1. Check balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Exit to ATM menu");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1 -> {
                    System.out.println("💰 Balance: " + user.getBalance());
                }

                case 2 -> {
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Print receipt? (true/false): ");
                    boolean receipt = scanner.nextBoolean();
                    scanner.nextLine();

                    atmService.deposit(user, amount, receipt);
                }

                case 3 -> {
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.print("Print receipt? (true/false): ");
                    boolean receipt = scanner.nextBoolean();
                    scanner.nextLine();

                    atmService.withdraw(user, amount, receipt);
                }

                case 4 -> {
                    System.out.print("Receiver username: ");
                    String receiver = scanner.nextLine();

                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    atmService.transfer(user, receiver, amount);
                }

                case 5 -> loggedIn = false;

                default -> System.out.println("❌ Invalid option");
            }
        }
    }

    // =========================
    // TECHNICIAN LOGIN
    // =========================
    private static void technicianLogin() {

        TechnicianService technicianService = new TechnicianService();

        System.out.print("Technician username: ");
        String username = scanner.nextLine();

        System.out.print("Technician password: ");
        String password = scanner.nextLine();

        if (technicianService.login(username, password)) {
            technicianMenu();
        } else {
            System.out.println("❌ Invalid technician credentials");
        }
    }

    // =========================
    // TECHNICIAN MENU (VIEW ONLY)
    // =========================
    private static void technicianMenu() {

        boolean running = true;

        while (running) {
            System.out.println("\n===== TECHNICIAN MENU =====");
            System.out.println("1. View ATM status");
            System.out.println("2. Exit to ATM menu");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    ATM atm = atmRepository.loadATM();
                    System.out.println("💰 Cash available: " + atm.getCash());
                    System.out.println("🧾 Receipt paper left: " + atm.getReceiptPaper());
                }
                case 2 -> running = false;
                default -> System.out.println("❌ Invalid option");
            }
        }
    }
}
