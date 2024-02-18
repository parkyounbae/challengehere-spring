package com.parkyounbae.challengehere.repository.jpa.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeInvitation;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeInvitationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaChallengeInvitationRepository extends JpaRepository<ChallengeInvitation, Long>, ChallengeInvitationRepository {
}
