package com.parkyounbae.challengehere.repository.interfaces.user;

import com.parkyounbae.challengehere.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository
public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByName(String name);

    Optional<User> findByProviderId(String provider, String providerId);

    void deleteById(Long id);

    List<User> findAllUsersWithName(String name);

}
