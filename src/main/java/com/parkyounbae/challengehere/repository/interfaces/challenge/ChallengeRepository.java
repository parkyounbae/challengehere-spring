package com.parkyounbae.challengehere.repository.interfaces.challenge;

import com.parkyounbae.challengehere.domain.challenge.Challenge;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChallengeRepository {
    Challenge save(Challenge challenge);

    Optional<Challenge> findById(Long id);

    void updateById(Long id, Challenge challenge);

    void deleteById(Long id);
}
