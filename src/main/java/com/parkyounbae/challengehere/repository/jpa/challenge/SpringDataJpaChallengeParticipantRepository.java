package com.parkyounbae.challengehere.repository.jpa.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeParticipant;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeParticipantRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaChallengeParticipantRepository extends JpaRepository<ChallengeParticipant, Long>, ChallengeParticipantRepository {
}
