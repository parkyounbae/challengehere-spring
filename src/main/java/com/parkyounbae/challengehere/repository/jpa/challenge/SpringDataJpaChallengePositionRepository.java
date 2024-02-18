package com.parkyounbae.challengehere.repository.jpa.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengePosition;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengePositionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaChallengePositionRepository extends JpaRepository<ChallengePosition, Long>, ChallengePositionRepository {
}
