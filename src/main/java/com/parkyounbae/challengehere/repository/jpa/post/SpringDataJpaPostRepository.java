package com.parkyounbae.challengehere.repository.jpa.post;

import com.parkyounbae.challengehere.domain.post.Post;
import com.parkyounbae.challengehere.repository.interfaces.post.PostRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaPostRepository extends JpaRepository<Post, Long>, PostRepository {
}
