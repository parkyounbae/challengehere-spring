package com.parkyounbae.challengehere.repository.jpa.challenge;

import com.parkyounbae.challengehere.domain.challenge.ChallengeSuccess;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeSuccessRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataJpaChallengeSuccessRepository extends JpaRepository<ChallengeSuccess, Long>, ChallengeSuccessRepository {
    @Query("SELECT cs.userId FROM ChallengeSuccess cs WHERE cs.challengeId = :challengeId AND cs.date = :date")
    List<Long> findUserIdsByChallengeIdAndDate(@Param("challengeId") Long challengeId, @Param("date") String date);

}
