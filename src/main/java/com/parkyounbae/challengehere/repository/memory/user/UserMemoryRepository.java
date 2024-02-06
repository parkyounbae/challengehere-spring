package com.parkyounbae.challengehere.repository.memory.user;

import com.parkyounbae.challengehere.domain.user.User;
import com.parkyounbae.challengehere.repository.interfaces.user.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserMemoryRepository implements UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        User user = users.get(id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<User> findByProviderId(String provider, String providerId) {
        return users.values().stream()
                .filter(user -> user.getProvider().equals(provider) && user.getProviderId().equals(providerId))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }

    @Override
    public List<User> findAllUsersWithName(String name) {
        return users.values().stream()
                .filter(user -> user.getName().equals(name) || user.getName().contains(name))
                .collect(Collectors.toList());
    }
}
