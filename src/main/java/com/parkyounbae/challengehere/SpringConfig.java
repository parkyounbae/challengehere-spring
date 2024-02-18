package com.parkyounbae.challengehere;

import com.parkyounbae.challengehere.repository.interfaces.challenge.*;
import com.parkyounbae.challengehere.repository.interfaces.post.LikeRepository;
import com.parkyounbae.challengehere.repository.interfaces.post.PostPhotoRepository;
import com.parkyounbae.challengehere.repository.interfaces.post.PostRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.FriendshipRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.UserRepository;
import com.parkyounbae.challengehere.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    private final PostRepository postRepository;
    private final PostPhotoRepository postPhotoRepository;
    private final LikeRepository likeRepository;

    private final ChallengeRepository challengeRepository;
    private final ChallengeInvitationRepository challengeInvitationRepository;
    private final ChallengePositionRepository challengePositionRepository;
    private final ChallengeParticipantRepository challengeParticipantRepository;
    private final ChallengeSuccessRepository challengeSuccessRepository;

    private final DailyUpdateService dailyUpdateService;



    @Autowired
    public SpringConfig(UserRepository userRepository,
                        FriendshipRepository friendshipRepository,
                        PostRepository postRepository,
                        PostPhotoRepository postPhotoRepository,
                        LikeRepository likeRepository,
                        ChallengeRepository challengeRepository,
                        ChallengeInvitationRepository challengeInvitationRepository,
                        ChallengePositionRepository challengePositionRepository,
                        ChallengeParticipantRepository challengeParticipantRepository,
                        ChallengeSuccessRepository challengeSuccessRepository,
                        DailyUpdateService dailyUpdateService
                        ) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
        this.postRepository = postRepository;
        this.postPhotoRepository = postPhotoRepository;
        this.likeRepository = likeRepository;
        this.challengeRepository = challengeRepository;
        this.challengeInvitationRepository = challengeInvitationRepository;
        this.challengePositionRepository = challengePositionRepository;
        this.challengeParticipantRepository = challengeParticipantRepository;
        this.challengeSuccessRepository = challengeSuccessRepository;
        this.dailyUpdateService = dailyUpdateService;
    }

    @Bean
    public UserService userService() {
        return new UserService( userRepository,
                 friendshipRepository,
                 challengeRepository,
                 challengeSuccessRepository,
                 challengeInvitationRepository,
                 challengeParticipantRepository,
                 challengePositionRepository,dailyUpdateService);
    }

    @Bean
    public ChallengeService challengeService() {
        return new ChallengeService( userRepository,
                 friendshipRepository,
                 challengeRepository,
                 challengeSuccessRepository,
                 challengeInvitationRepository,
                 challengeParticipantRepository,
                 challengePositionRepository,
                 postRepository,likeRepository,dailyUpdateService
                );
    }

    @Bean
    public PostService postService() {
        return new PostService( postRepository,  postPhotoRepository,  likeRepository, userRepository,dailyUpdateService);
    }

    @Bean
    public FriendService friendService() {
        return new FriendService( friendshipRepository,  userRepository,  challengeRepository, challengeSuccessRepository);
    }

}
