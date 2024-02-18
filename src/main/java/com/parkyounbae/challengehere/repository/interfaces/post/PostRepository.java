package com.parkyounbae.challengehere.repository.interfaces.post;

import com.parkyounbae.challengehere.domain.post.Post;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository {
    Post save(Post post);

    Optional<Post> findById(Long id);

    List<Post> findByChallengeId(Long id);

    // void updateById(Long id, Post post);

    void deleteById(Long id);

    void deleteByChallengeId(Long challengeId);
}
