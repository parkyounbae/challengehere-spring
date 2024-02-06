package com.parkyounbae.challengehere.repository.interfaces.post;

import com.parkyounbae.challengehere.domain.post.Like;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository {
    Like save(Like likeRepository);

    List<Like> findByPostId(Long id);

    Optional<Like> findByUserIdAndPostId(Long userId, Long postId);


    void deleteById(Long id);
}
