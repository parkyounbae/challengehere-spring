package com.parkyounbae.challengehere.repository.interfaces.user;

import com.parkyounbae.challengehere.domain.user.Friendship;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendshipRepository {
    Friendship save(Friendship friendship);
    Optional<Friendship> findById(Long id);

    List<Friendship> findByReceiveIdOrRequestId(Long receiveId, Long requestId); // 친구거나 요청보낸 사람 반환

    List<Friendship> findByReceiveId(Long id); // 내가 받은 친구요청

    Optional<Friendship> findByRequestIdAndReceiveId(Long receiveId, Long RequestId); // 특정 요청 반환

    List<Friendship> findMyFriends(Long id); // 친구 수락을 한 유저만

    // void updateById(Long id, Friendship friendship); // 수락했을 때

    void deleteById(Long Id); // 거절했을 때

    void deleteByUserId(Long id);
}
