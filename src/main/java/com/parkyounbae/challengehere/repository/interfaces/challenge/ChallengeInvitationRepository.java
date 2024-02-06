package com.parkyounbae.challengehere.repository.interfaces.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeInvitation;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeInvitationRepository {
    ChallengeInvitation save(ChallengeInvitation challengeInvitation);

    Optional<ChallengeInvitation> findById(Long id);

    List<ChallengeInvitation> findByUserId(Long id); // 친구거나 요청보낸 사람 반환

    Optional<ChallengeInvitation> findByChallengeIdAndReceiveId(Long challengeId, Long receiveId);

    void updateById(Long id, ChallengeInvitation challengeInvitation); // 수락했을 때

    void deleteById(Long Id); // 거절했을 때

    void deleteByReceiveId(Long id);
}
