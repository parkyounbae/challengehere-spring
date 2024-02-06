package com.parkyounbae.challengehere.repository.memory.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeParticipant;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeParticipantRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChallengeParticipantMemoryRepository implements ChallengeParticipantRepository {
    private final Map<Long, ChallengeParticipant> participants = new HashMap<>();
    private long nextId = 1;

    @Override
    public ChallengeParticipant save(ChallengeParticipant challengeParticipant) {
        if (challengeParticipant.getId() == null) {
            challengeParticipant.setId(nextId++);
        }
        participants.put(challengeParticipant.getId(), challengeParticipant);
        return challengeParticipant;
    }

    @Override
    public List<ChallengeParticipant> findBuUserId(Long userId) {
        List<ChallengeParticipant> userParticipants = new ArrayList<>();
        for (ChallengeParticipant participant : participants.values()) {
            if (participant.getUserId().equals(userId)) {
                userParticipants.add(participant);
            }
        }
        return userParticipants;
    }

    @Override
    public List<ChallengeParticipant> findByChallengeId(Long challengeId) {
        List<ChallengeParticipant> challengeParticipants = new ArrayList<>();
        for (ChallengeParticipant participant : participants.values()) {
            if (participant.getChallengeId().equals(challengeId)) {
                challengeParticipants.add(participant);
            }
        }
        return challengeParticipants;
    }

    @Override
    public void deleteById(Long id) {
        participants.remove(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        List<Long> participantsToRemove = new ArrayList<>();
        for (Map.Entry<Long, ChallengeParticipant> entry : participants.entrySet()) {
            Long participantId = entry.getKey();
            ChallengeParticipant participant = entry.getValue();
            if (participant.getUserId().equals(userId)) {
                participantsToRemove.add(participantId);
            }
        }

        for (Long participantId : participantsToRemove) {
            participants.remove(participantId);
        }
    }
}
