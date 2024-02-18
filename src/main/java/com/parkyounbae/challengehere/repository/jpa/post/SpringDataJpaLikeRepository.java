package com.parkyounbae.challengehere.repository.jpa.post;

import com.parkyounbae.challengehere.domain.post.Like;
import com.parkyounbae.challengehere.repository.interfaces.post.LikeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataJpaLikeRepository extends JpaRepository<Like, Long>, LikeRepository {
}
