package com.parkyounbae.challengehere.repository.memory.post;

import com.parkyounbae.challengehere.domain.post.Post;
import com.parkyounbae.challengehere.repository.interfaces.post.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PostMemoryRepository implements PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
    private long nextId = 1;

    @Override
    public Post save(Post post) {
        if (post.getId() == null) {
            post.setId(nextId++);
        }
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = posts.get(id);
        return Optional.ofNullable(post);
    }

    @Override
    public List<Post> findByChallengeId(Long challengeId) {
        List<Post> challengePosts = new ArrayList<>();
        for (Post post : posts.values()) {
            if (post.getChallengeId().equals(challengeId)) {
                challengePosts.add(post);
            }
        }
        return challengePosts;
    }

    @Override
    public void updateById(Long id, Post post) {
        // 게시물을 업데이트하는 로직을 구현합니다.
        // 해당 ID의 Post 객체를 가져와서 업데이트하는 로직을 구현하세요.
        posts.replace(id, post);
    }

    @Override
    public void deleteById(Long id) {
        // 게시물을 삭제하는 로직을 구현합니다.
        // 해당 ID의 Post 객체를 삭제하는 로직을 구현하세요.
        posts.remove(id);
    }
}
