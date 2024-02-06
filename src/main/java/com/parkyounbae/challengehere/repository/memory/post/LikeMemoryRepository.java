package com.parkyounbae.challengehere.repository.memory.post;

import com.parkyounbae.challengehere.domain.post.Like;
import com.parkyounbae.challengehere.repository.interfaces.post.LikeRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class LikeMemoryRepository implements LikeRepository {
    private final Map<Long, Like> likes = new HashMap<>();
    private long nextId = 1;

    @Override
    public Like save(Like like) {
        if (like.getId() == null) {
            like.setId(nextId++);
        }
        likes.put(like.getId(), like);
        return like;
    }

    @Override
    public List<Like> findByPostId(Long postId) {
        List<Like> postLikes = new ArrayList<>();
        for (Like like : likes.values()) {
            if (like.getPostId().equals(postId)) {
                postLikes.add(like);
            }
        }
        return postLikes;
    }

    @Override
    public Optional<Like> findByUserIdAndPostId(Long userId, Long postId) {
        for (Like like : likes.values()) {
            if (like.getUserId().equals(userId) && like.getPostId().equals(postId)) {
                return Optional.of(like);
            }
        }
        return Optional.empty();
    }


    @Override
    public void deleteById(Long id) {
        likes.remove(id);
    }
}
