package repositories.json;

import entities.ATM;
import repositories.ATMRepository;
import utils.JsonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JsonATMRepository implements ATMRepository {

    private static final String FILE = "data/atm.json";

    // =====================
    // LOAD ATM
    // =====================
    @Override
    public ATM loadATM() {

        String content = JsonUtils.readFile(FILE);
        if (content == null || content.isEmpty()) {
            // Default empty ATM
            return new ATM(new HashMap<>(), 0, false);
        }

        JSONObject root = new JSONObject(content);

        // Load operational flag
        boolean operational = root.getBoolean("operational");

        // Load receipt paper
        int receiptPaper = root.getInt("receiptPaper");

        // Load cash denominations
        Map<Integer, Integer> cash = new HashMap<>();
        JSONObject cashObj = root.getJSONObject("cash");

        Iterator<String> keys = cashObj.keys();
        while (keys.hasNext()) {
            String denominationStr = keys.next();
            int denomination = Integer.parseInt(denominationStr);
            int quantity = cashObj.getInt(denominationStr);
            cash.put(denomination, quantity);
        }

        return new ATM(cash, receiptPaper, operational);
    }

    // =====================
    // SAVE ATM
    // =====================
    @Override
    public void saveATM(ATM atm) {

        JSONObject root = new JSONObject();
        JSONObject cashJson = new JSONObject();

        for (Map.Entry<Integer, Integer> entry : atm.getCash().entrySet()) {
            cashJson.put(String.valueOf(entry.getKey()), entry.getValue());
        }

        root.put("operational", atm.isOperational());
        root.put("cash", cashJson);
        root.put("receiptPaper", atm.getReceiptPaper());

        JsonUtils.writeFile(FILE, root.toString(2));
    }
}