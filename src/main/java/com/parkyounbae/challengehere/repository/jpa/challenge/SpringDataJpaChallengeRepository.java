package com.parkyounbae.challengehere.repository.jpa.challenge;

import com.parkyounbae.challengehere.domain.challenge.Challenge;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaChallengeRepository extends JpaRepository<Challenge, Long>, ChallengeRepository {
}
