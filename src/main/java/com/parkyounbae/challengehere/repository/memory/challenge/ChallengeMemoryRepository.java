package com.parkyounbae.challengehere.repository.memory.challenge;

import com.parkyounbae.challengehere.domain.challenge.Challenge;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ChallengeMemoryRepository implements ChallengeRepository {
    private final Map<Long, Challenge> challenges = new HashMap<>();
    private long nextId = 1;

    @Override
    public Challenge save(Challenge challenge) {
        if (challenge.getId() == null) {
            challenge.setId(nextId++);
        }
        challenges.put(challenge.getId(), challenge);
        return challenge;
    }

    @Override
    public Optional<Challenge> findById(Long id) {
        Challenge challenge = challenges.get(id);
        return Optional.ofNullable(challenge);
    }

    @Override
    public void updateById(Long id, Challenge challenge) {
        // 도전과제 정보를 업데이트하는 로직을 구현합니다.
        // 해당 ID의 Challenge 객체를 가져와서 업데이트하는 로직을 구현하세요.
        challenges.put(id, challenge);
    }

    @Override
    public void deleteById(Long id) {
        challenges.remove(id);
    }
}
