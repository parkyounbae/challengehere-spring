package com.parkyounbae.challengehere.repository.memory.post;

import com.parkyounbae.challengehere.domain.post.PostPhoto;
import com.parkyounbae.challengehere.repository.interfaces.post.PostPhotoRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
@Repository
public class PostPhotoMemoryRepository implements PostPhotoRepository {
    private final Map<Long, PostPhoto> postPhotos = new HashMap<>();
    private long nextId = 1;

    @Override
    public PostPhoto save(PostPhoto postPhoto) {
        if (postPhoto.getId() == null) {
            postPhoto.setId(nextId++);
        }
        postPhotos.put(postPhoto.getId(), postPhoto);
        return postPhoto;
    }

    @Override
    public Optional<PostPhoto> findById(Long id) {
        PostPhoto postPhoto = postPhotos.get(id);
        return Optional.ofNullable(postPhoto);
    }

    @Override
    public List<PostPhoto> findBuPostId(Long postId) {
        List<PostPhoto> postPhotosForPost = new ArrayList<>();
        for (PostPhoto postPhoto : postPhotos.values()) {
            if (postPhoto.getPostId().equals(postId)) {
                postPhotosForPost.add(postPhoto);
            }
        }
        return postPhotosForPost;
    }

    @Override
    public void updateById(Long id, PostPhoto postPhoto) {
        // 게시물 사진 정보를 업데이트하는 로직을 구현합니다.
        // 해당 ID의 PostPhoto 객체를 가져와서 업데이트하는 로직을 구현하세요.
        postPhotos.replace(id, postPhoto);
    }

    @Override
    public void deleteById(Long id) {
        // 게시물 사진 정보를 삭제하는 로직을 구현합니다.
        // 해당 ID의 PostPhoto 객체를 삭제하는 로직을 구현하세요.
        postPhotos.remove(id);
    }
}
