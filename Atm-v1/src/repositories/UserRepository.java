package repositories;

import entities.User;
import java.util.List;

public interface UserRepository {

    User findByUsername(String username);

    void update(User user);

    // ✅ ADMIN VIEW ONLY
    List<User> findAll();
}