package repositories.json;

import entities.User;
import repositories.UserRepository;
import utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUserRepository implements UserRepository {

    private static final String FILE = "data/users.json";

    @Override
    public User findByUsername(String username) {

        String content = JsonUtils.readFile(FILE);
        if (content == null || content.isEmpty()) return null;

        JSONArray users = new JSONArray(content);

        for (int i = 0; i < users.length(); i++) {
            JSONObject o = users.getJSONObject(i);

            if (o.getString("username").equals(username)) {
                return new User(
                        o.getString("username"),
                        o.getString("pin"),
                        o.getDouble("balance"),
                        o.getInt("failedAttempts"),
                        o.getBoolean("locked")
                );
            }
        }
        return null;
    }

    @Override
    public void update(User user) {

        JSONArray users = new JSONArray(JsonUtils.readFile(FILE));

        for (int i = 0; i < users.length(); i++) {
            JSONObject o = users.getJSONObject(i);

            if (o.getString("username").equals(user.getUsername())) {
                o.put("balance", user.getBalance());
                o.put("failedAttempts", user.getFailedAttempts());
                o.put("locked", user.isLocked());
                break;
            }
        }

        JsonUtils.writeFile(FILE, users.toString(2));
    }

    // =====================
    // ADMIN VIEW ONLY
    // =====================
    @Override
    public List<User> findAll() {

        List<User> result = new ArrayList<>();

        String content = JsonUtils.readFile(FILE);
        if (content == null || content.isEmpty()) return result;

        JSONArray users = new JSONArray(content);

        for (int i = 0; i < users.length(); i++) {
            JSONObject o = users.getJSONObject(i);

            result.add(new User(
                    o.getString("username"),
                    o.getString("pin"),
                    o.getDouble("balance"),
                    o.getInt("failedAttempts"),
                    o.getBoolean("locked")
            ));
        }

        return result;
    }
}