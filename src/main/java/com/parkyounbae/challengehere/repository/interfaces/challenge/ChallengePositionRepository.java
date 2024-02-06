package com.parkyounbae.challengehere.repository.interfaces.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengePosition;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengePositionRepository {
    ChallengePosition save(ChallengePosition challengePosition);

    List<ChallengePosition> findByChallengeId(Long challengeId);

    void updateById(Long id, ChallengePosition challengePosition);

    void deleteById(Long id);

    void deleteByChallengeId(Long challengeId);
}
