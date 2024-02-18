package com.parkyounbae.challengehere.repository.jpa.user;

import com.parkyounbae.challengehere.domain.user.Friendship;
import com.parkyounbae.challengehere.repository.interfaces.user.FriendshipRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataJpaFriendshipRepository extends JpaRepository<Friendship, Long>, FriendshipRepository {
    @Query("SELECT f FROM Friendship f WHERE f.receiveId = :id AND f.status = 0")
    List<Friendship> findByReceiveIdAndStatus(Long id);

    @Query("SELECT f FROM Friendship f WHERE (f.requestId = :userId OR f.receiveId = :userId) AND f.status = 1")
    List<Friendship> findMyFriends(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Friendship f WHERE f.requestId = :userId OR f.receiveId = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
