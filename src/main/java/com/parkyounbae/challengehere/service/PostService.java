package com.parkyounbae.challengehere.service;

import com.parkyounbae.challengehere.domain.post.Like;
import com.parkyounbae.challengehere.domain.post.Post;
import com.parkyounbae.challengehere.domain.post.PostPhoto;
import com.parkyounbae.challengehere.domain.user.User;
import com.parkyounbae.challengehere.dto.board.GetBoardResponse;
import com.parkyounbae.challengehere.dto.board.PostBoardRequest;
import com.parkyounbae.challengehere.repository.interfaces.post.LikeRepository;
import com.parkyounbae.challengehere.repository.interfaces.post.PostPhotoRepository;
import com.parkyounbae.challengehere.repository.interfaces.post.PostRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.UserRepository;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostPhotoRepository postPhotoRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final DailyUpdateService dailyUpdateService;

    @Autowired
    public PostService(PostRepository postRepository, PostPhotoRepository postPhotoRepository, LikeRepository likeRepository, UserRepository userRepository, DailyUpdateService dailyUpdateService) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.postPhotoRepository = postPhotoRepository;
        this.userRepository = userRepository;
        this.dailyUpdateService = dailyUpdateService;
    }

    public String getCurrentDate() {
        return dailyUpdateService.getCurrentDateString();
    }

    // 게시글 리스트 불러오기 : PostRepo, PostPhotoRepo, LikeRepo
    public List<GetBoardResponse> getBoardResponses(Long challengeId, Long userId) {
        List<GetBoardResponse> getBoardResponseList = new ArrayList<>();

        List<Post> postList = postRepository.findByChallengeId(challengeId);

        for(Post post : postList) {
            GetBoardResponse getBoardResponse = new GetBoardResponse();

            // 게시글 id
            getBoardResponse.setId(post.getId());

            // 게시글 내용
            getBoardResponse.setContent(post.getContent());
            getBoardResponse.setUserId(userId);

            // 작성자 이름
            Optional<User> userOptional = userRepository.findById(post.getUserId());

            if(userOptional.isPresent()) {
                getBoardResponse.setName(userOptional.get().getName());
            } else {
                getBoardResponse.setName("알수없음");
            }

            // 좋아요 수
            List<Like> likeList = likeRepository.findByPostId(post.getId());
            getBoardResponse.setLike(likeList.size());

            // 유저가 좋아요 눌렀는지
            Optional<Like> likeOptional = likeRepository.findByUserIdAndPostId(userId, post.getId());

            if(likeOptional.isPresent()) {
                getBoardResponse.setIsLiked(true);
            } else {
                getBoardResponse.setIsLiked(false);
            }

            // 이미지
            List<PostPhoto> postPhotoList = postPhotoRepository.findBuPostId(post.getId());
            List<String> photoListPathList = new ArrayList<>();

            for(PostPhoto p : postPhotoList) {
                photoListPathList.add(p.getPath());
            }

            getBoardResponse.setImagePath(photoListPathList);

            getBoardResponseList.add(getBoardResponse);

        }

        return getBoardResponseList;
    }

    // 게시글 등록하기 : PostRepo, PostPhotoRepo
    public void postBoard(PostBoardRequest postBoardRequest) {
        Post post = new Post();

        post.setContent(postBoardRequest.getContent());
        post.setDate(getCurrentDate());
        post.setChallengeId(postBoardRequest.getChallengeId());
        post.setUserId(postBoardRequest.getUserId());

        Post saved = postRepository.save(post);

        if(!postBoardRequest.getImages().isEmpty()) {
            for(String s : postBoardRequest.getImages()) {
                PostPhoto postPhoto = new PostPhoto();

                postPhoto.setPostId(saved.getId());
                postPhoto.setPath(s);

                postPhotoRepository.save(postPhoto);
            }
        }

    }

    // 게시글 삭제하기 : PostRepo, PostPhotoRepo, LikeRepo
    public void deleteBoard(Long postId, Long userId) {
        // todo
        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty()) {
            return;
        }

        if(!post.get().getUserId().equals(userId)) {
            return;
        }

        List<Like> likeList = likeRepository.findByPostId(postId);

        for(Like l : likeList) {
            likeRepository.deleteById(l.getId());
        }

        postRepository.deleteById(postId);
    }

    // 게시글 좋아요 누르기
    public void likeBoard(Long postId, Long userId) {
        Optional<Like> myLike = likeRepository.findByUserIdAndPostId(userId, postId);

        if(myLike.isPresent()) {
            return;
        } else {
            Like newLike = new Like();
            newLike.setPostId(postId);
            newLike.setUserId(userId);
            likeRepository.save(newLike);
            return;
        }
    }
}
