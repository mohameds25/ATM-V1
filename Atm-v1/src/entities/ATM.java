package entities;

import java.util.Map;
import java.util.TreeMap;

public class ATM {

    private Map<Integer, Integer> cash;   // denomination -> count
    private int receiptPaper;
    private boolean operational;

    public ATM(Map<Integer, Integer> cash, int receiptPaper, boolean operational) {
        // TreeMap to keep denominations sorted (high → low)
        this.cash = new TreeMap<>((a, b) -> b - a);
        this.cash.putAll(cash);
        this.receiptPaper = receiptPaper;
        this.operational = operational;
    }

    // =====================
    // CHECK TOTAL CASH
    // =====================
    public boolean hasEnoughCash(int amount) {
        int total = 0;
        for (Map.Entry<Integer, Integer> e : cash.entrySet()) {
            total += e.getKey() * e.getValue();
        }
        return total >= amount;
    }

    // =====================
    // DISPENSE CASH (NO LAMBDAS)
    // =====================
    public void dispenseCash(int amount) {
        int remaining = amount;

        for (Map.Entry<Integer, Integer> entry : cash.entrySet()) {
            int denomination = entry.getKey();
            int availableNotes = entry.getValue();

            if (remaining <= 0) break;

            int neededNotes = remaining / denomination;
            int usedNotes = Math.min(availableNotes, neededNotes);

            if (usedNotes > 0) {
                cash.put(denomination, availableNotes - usedNotes);
                remaining -= usedNotes * denomination;
            }
        }
    }

    // =====================
    // RECEIPT PAPER
    // =====================
    public int getReceiptPaper() {
        return receiptPaper;
    }

    public void useReceiptPaper() {
        if (receiptPaper > 0) {
            receiptPaper--;
        }
    }

    // =====================
    // GETTERS
    // =====================
    public Map<Integer, Integer> getCash() {
        return cash;
    }

    public boolean isOperational() {
        return operational;
    }
}