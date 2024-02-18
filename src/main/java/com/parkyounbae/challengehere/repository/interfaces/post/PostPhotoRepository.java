package com.parkyounbae.challengehere.repository.interfaces.post;

import com.parkyounbae.challengehere.domain.post.PostPhoto;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostPhotoRepository {
    PostPhoto save(PostPhoto postPhoto);

    Optional<PostPhoto> findById(Long id);

    List<PostPhoto> findByPostId(Long id);

    // void updateById(Long id, PostPhoto postPhoto);

    void deleteById(Long id);
}
