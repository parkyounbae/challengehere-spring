package com.parkyounbae.challengehere.repository.jpa.post;

import com.parkyounbae.challengehere.domain.post.PostPhoto;
import com.parkyounbae.challengehere.repository.interfaces.post.PostPhotoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaPostPhotoRepository extends JpaRepository<PostPhoto, Long>, PostPhotoRepository {
}
