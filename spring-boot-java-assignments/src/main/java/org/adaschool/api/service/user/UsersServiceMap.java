package org.adaschool.api.service.user;

import org.adaschool.api.repository.user.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServiceMap implements UsersService {

    private final Map<String, User> usersMap = new HashMap<>();

    @Override
    public User save(User user) {
        usersMap.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(usersMap.get(id));
    }

    @Override
    public List<User> all() {
        return new ArrayList<>(usersMap.values());
    }

    @Override
    public void deleteById(String id) {
        usersMap.remove(id);
    }

    @Override
    public User update(User user, String userId) {
        usersMap.put(userId, user);
        return user;
    }
}
