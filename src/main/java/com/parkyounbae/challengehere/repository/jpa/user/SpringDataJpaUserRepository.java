package com.parkyounbae.challengehere.repository.jpa.user;

import com.parkyounbae.challengehere.domain.user.User;
import com.parkyounbae.challengehere.repository.interfaces.user.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaUserRepository extends JpaRepository<User, Long>, UserRepository {
    @Override
    User save(User user);

    @Override
    Optional<User> findByName(String name);

    @Override
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

    @Override
    void deleteById(Long id);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findAllUsersWithName(String name);
}
