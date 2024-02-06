package com.parkyounbae.challengehere.repository.memory.user;

import com.parkyounbae.challengehere.domain.user.Friendship;
import com.parkyounbae.challengehere.repository.interfaces.user.FriendshipRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class FriendshipMemoryRepository implements FriendshipRepository {
    private final Map<Long, Friendship> friendships = new HashMap<>();
    private long nextId = 1;

    @Override
    public Friendship save(Friendship friendship) {
        if (friendship.getId() == null) {
            friendship.setId(nextId++);
        }
        friendships.put(friendship.getId(), friendship);
        return friendship;
    }

    @Override
    public Optional<Friendship> findById(Long id) {
        Friendship friendship = friendships.get(id);
        return Optional.ofNullable(friendship);
    }

    @Override
    public List<Friendship> findByUserId(Long userId) {
        List<Friendship> userFriendships = new ArrayList<>();
        for (Friendship friendship : friendships.values()) {
            if (friendship.getRequestId().equals(userId) || friendship.getReceiveId().equals(userId)) {
                userFriendships.add(friendship);
            }
        }
        return userFriendships;
    }

    @Override
    public List<Friendship> findByReceiveId(Long id) {
        List<Friendship> userFriendships = new ArrayList<>();
        for (Friendship friendship : friendships.values()) {
            if (friendship.getStatus() == 0 || friendship.getReceiveId().equals(id)) {
                userFriendships.add(friendship);
            }
        }
        return userFriendships;
    }

    @Override
    public Optional<Friendship> findByRequestIdAndReceiveId(Long receiveId, Long requestId) {
        for (Friendship friendship : friendships.values()) {
            if (friendship.getRequestId().equals(requestId) && friendship.getReceiveId().equals(receiveId)) {
                return Optional.of(friendship);
            }
        }
        return Optional.empty(); // 일치하는 객체가 없을 경우 Optional.empty() 반환
    }

    @Override
    public List<Friendship> findMyFriends(Long userId) {
        List<Friendship> userFriendships = new ArrayList<>();
        for (Friendship friendship : friendships.values()) {
            if ((friendship.getRequestId().equals(userId) || friendship.getReceiveId().equals(userId)) && friendship.getStatus() == 1) {
                userFriendships.add(friendship);
            }
        }
        return userFriendships;
    }

    @Override
    public void updateById(Long id, Friendship friendship) {
        // 친구 요청 수락 시 필요한 로직을 구현합니다.
        friendships.replace(id, friendship);
    }

    @Override
    public void deleteById(Long id) {
        // 친구 요청 거절 시 필요한 로직을 구현합니다.
        friendships.remove(id);
    }

    @Override
    public void deleteByUserId(Long userId) {
        List<Long> friendshipsToRemove = new ArrayList<>();
        for (Map.Entry<Long, Friendship> entry : friendships.entrySet()) {
            Long friendshipId = entry.getKey();
            Friendship friendship = entry.getValue();
            if (friendship.getRequestId().equals(userId) || friendship.getReceiveId().equals(userId)) {
                friendshipsToRemove.add(friendshipId);
            }
        }

        for (Long friendshipId : friendshipsToRemove) {
            friendships.remove(friendshipId);
        }
    }

}
