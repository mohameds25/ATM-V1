package repositories.json;

import entities.Transaction;
import entities.TransactionType;
import repositories.TransactionRepository;
import utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JsonTransactionRepository implements TransactionRepository {

    private static final String FILE = "data/transactions.json";

    @Override
    public void save(Transaction transaction) {

        JSONArray transactions;
        String content = JsonUtils.readFile(FILE);

        if (content == null || content.isEmpty()) {
            transactions = new JSONArray();
        } else {
            transactions = new JSONArray(content);
        }

        JSONObject obj = new JSONObject();
        obj.put("username", transaction.getUsername());
        obj.put("amount", transaction.getAmount());
        obj.put("type", transaction.getType().name()); // ✅ ENUM → String
        obj.put("receiptPrinted", transaction.isReceiptPrinted());
        obj.put("timestamp", transaction.getTimestamp().toString());

        transactions.put(obj);
        JsonUtils.writeFile(FILE, transactions.toString(2));
    }

    @Override
    public List<Transaction> findByUsername(String username) {

        List<Transaction> result = new ArrayList<>();
        String content = JsonUtils.readFile(FILE);

        if (content == null || content.isEmpty()) {
            return result;
        }

        JSONArray transactions = new JSONArray(content);

        for (int i = 0; i < transactions.length(); i++) {

            JSONObject obj = transactions.getJSONObject(i);

            if (obj.getString("username").equals(username)) {

                TransactionType type = obj.has("type")
                        ? TransactionType.valueOf(obj.getString("type"))
                        : TransactionType.WITHDRAW; // safe default

                Transaction transaction = new Transaction(
                        obj.getString("username"),
                        obj.getInt("amount"),
                        type,
                        obj.getBoolean("receiptPrinted"),
                        LocalDateTime.parse(obj.getString("timestamp"))
                );

                result.add(transaction);
            }
        }

        return result;
    }
}