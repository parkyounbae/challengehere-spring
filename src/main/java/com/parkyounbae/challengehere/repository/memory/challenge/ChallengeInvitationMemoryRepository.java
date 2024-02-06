package com.parkyounbae.challengehere.repository.memory.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeInvitation;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeInvitationRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ChallengeInvitationMemoryRepository implements ChallengeInvitationRepository {
    private final Map<Long, ChallengeInvitation> invitations = new HashMap<>();
    private long nextId = 1;

    @Override
    public ChallengeInvitation save(ChallengeInvitation challengeInvitation) {
        if (challengeInvitation.getId() == null) {
            challengeInvitation.setId(nextId++);
        }
        invitations.put(challengeInvitation.getId(), challengeInvitation);
        return challengeInvitation;
    }

    @Override
    public Optional<ChallengeInvitation> findById(Long id) {
        ChallengeInvitation invitation = invitations.get(id);
        return Optional.ofNullable(invitation);
    }

    @Override
    public List<ChallengeInvitation> findByUserId(Long userId) {
        List<ChallengeInvitation> userInvitations = new ArrayList<>();
        for (ChallengeInvitation invitation : invitations.values()) {
            if (invitation.getReceiveId().equals(userId)) {
                userInvitations.add(invitation);
            }
        }
        return userInvitations;
    }

    @Override
    public Optional<ChallengeInvitation> findByChallengeIdAndReceiveId(Long challengeId, Long receiveId) {
        for(ChallengeInvitation c : invitations.values()) {
            if(c.getChallengeId().equals(challengeId) && c.getReceiveId().equals(receiveId)) {
                return Optional.of(c);
            }
        }
        return Optional.empty(); // 일치하는 객체가 없을 경우 Optional.empty() 반환
    }

    @Override
    public void updateById(Long id, ChallengeInvitation challengeInvitation) {
        // 도전과제 초대를 수락하는 로직을 구현합니다.
        // 해당 ID의 ChallengeInvitation 객체를 가져와서 상태를 업데이트하는 로직을 구현하세요.
        invitations.replace(id, challengeInvitation);
    }

    @Override
    public void deleteById(Long id) {
        // 도전과제 초대를 거절하는 로직을 구현합니다.
        // 해당 ID의 ChallengeInvitation 객체를 삭제하는 로직을 구현하세요.
        invitations.remove(id);
    }

    @Override
    public void deleteByReceiveId(Long receiveId) {
        List<Long> invitationsToRemove = new ArrayList<>();
        for (Map.Entry<Long, ChallengeInvitation> entry : invitations.entrySet()) {
            Long invitationId = entry.getKey();
            ChallengeInvitation invitation = entry.getValue();
            if (invitation.getReceiveId().equals(receiveId)) {
                invitationsToRemove.add(invitationId);
            }
        }

        for (Long invitationId : invitationsToRemove) {
            invitations.remove(invitationId);
        }
    }

}
