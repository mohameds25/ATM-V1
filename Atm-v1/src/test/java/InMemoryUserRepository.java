import entities.User;
import repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void update(User user) {
        users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        users.add(user);
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}