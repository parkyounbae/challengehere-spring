package com.parkyounbae.challengehere.repository.memory.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengePosition;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengePositionRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ChallengePositionMemoryRepository implements ChallengePositionRepository {
    private final Map<Long, ChallengePosition> positions = new HashMap<>();
    private long nextId = 1;

    @Override
    public ChallengePosition save(ChallengePosition challengePosition) {
        if (challengePosition.getId() == null) {
            challengePosition.setId(nextId++);
        }
        positions.put(challengePosition.getId(), challengePosition);
        return challengePosition;
    }

    @Override
    public List<ChallengePosition> findByChallengeId(Long challengeId) {
        List<ChallengePosition> challengePositions = new ArrayList<>();
        for (ChallengePosition position : positions.values()) {
            if (position.getChallengeId().equals(challengeId)) {
                challengePositions.add(position);
            }
        }
        return challengePositions;
    }

    @Override
    public void updateById(Long id, ChallengePosition challengePosition) {
        // 도전과제 포지션 정보를 업데이트하는 로직을 구현합니다.
        positions.replace(id, challengePosition);
    }

    @Override
    public void deleteById(Long id) {
        positions.remove(id);
    }

    @Override
    public void deleteByChallengeId(Long challengeId) {
        // 특정 도전과제에 속한 포지션 정보를 삭제하는 로직을 구현합니다.
        // 해당 도전과제 ID에 속한 모든 ChallengePosition 객체를 삭제하는 로직을 구현하세요.
        List<Long> positionsToRemove = new ArrayList<>();
        for (Map.Entry<Long, ChallengePosition> entry : positions.entrySet()) {
            if (entry.getValue().getChallengeId().equals(challengeId)) {
                positionsToRemove.add(entry.getKey());
            }
        }

        for (Long positionId : positionsToRemove) {
            positions.remove(positionId);
        }
    }
}
