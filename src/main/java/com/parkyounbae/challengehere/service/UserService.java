package com.parkyounbae.challengehere.service;

import com.parkyounbae.challengehere.domain.challenge.*;
import com.parkyounbae.challengehere.domain.user.Friendship;
import com.parkyounbae.challengehere.domain.user.User;
import com.parkyounbae.challengehere.dto.alert.GetAlertResponse;
import com.parkyounbae.challengehere.dto.alert.PostAlertResponse;
import com.parkyounbae.challengehere.dto.challenge.GetFriendListResponse;
import com.parkyounbae.challengehere.dto.home.CircleData;
import com.parkyounbae.challengehere.dto.home.GetHomeResponse;
import com.parkyounbae.challengehere.dto.home.HomeChallengeData;
import com.parkyounbae.challengehere.repository.interfaces.challenge.*;
import com.parkyounbae.challengehere.repository.interfaces.user.FriendshipRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.UserRepository;
//import jakarta.transaction.Transactional;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeSuccessRepository challengeSuccessRepository;
    private final ChallengeInvitationRepository challengeInvitationRepository;
    private final ChallengeParticipantRepository challengeParticipantRepository;
    private final ChallengePositionRepository challengePositionRepository;
    private final DailyUpdateService dailyUpdateService;

    @Autowired
    public UserService(UserRepository userRepository,
                       FriendshipRepository friendshipRepository,
                       ChallengeRepository challengeRepository,
                       ChallengeSuccessRepository challengeSuccessRepository,
                       ChallengeInvitationRepository challengeInvitationRepository,
                       ChallengeParticipantRepository challengeParticipantRepository,
                       ChallengePositionRepository challengePositionRepository,
                       DailyUpdateService dailyUpdateService
                       ) {
        this.userRepository = userRepository;
        this.challengeInvitationRepository = challengeInvitationRepository;
        this.challengeParticipantRepository = challengeParticipantRepository;
        this.challengeRepository = challengeRepository;
        this.challengeSuccessRepository = challengeSuccessRepository;
        this.friendshipRepository = friendshipRepository;
        this.challengePositionRepository = challengePositionRepository;
        this.dailyUpdateService = dailyUpdateService;
    }

    public String getCurrentDate() {
        return dailyUpdateService.getCurrentDateString();
    }

    // 신규 회원 가입 : UserRepo
    public Long signUp(String name, String token, String provider) {

        User user = new User();
        user.setName(name);
        user.setProvider(provider);
        user.setProviderId(token);

        userRepository.save(user);

        return user.getId();
    }

    // 소셜 로그인 : UserRepo
    public Optional<User> login(String providerToken,String provider) {
        return userRepository.findByProviderAndProviderId(provider, providerToken);
    }

    // 회원 탈퇴하기
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        friendshipRepository.deleteByUserId(id);

        challengeSuccessRepository.deleteByUserId(id);
        challengeParticipantRepository.deleteByUserId(id);
        challengeInvitationRepository.deleteByReceiveId(id);
    }

    // 이름이 중복인지 아닌지 확인하는 : UserRepo
    public Boolean checkDuplicate(String name) {
        Optional<User> user = userRepository.findByName(name);

        return user.isPresent();
    }

    // 알림 리스트 반환하기 : FriendshipRepo, ChallengeInvitationRepo
    public List<GetAlertResponse> getAlertList(Long id) {
        List<GetAlertResponse> getAlertResponses = new ArrayList<GetAlertResponse>();

        List<ChallengeInvitation> invitations = challengeInvitationRepository.findByReceiveId(id);
        List<Friendship> friendships = friendshipRepository.findByReceiveId(id);

        for(ChallengeInvitation c : invitations) {
            GetAlertResponse getAlertResponse = new GetAlertResponse();

            Optional<String> challengeName = challengeRepository.findById(c.getChallengeId())
                    .map(challenge -> challenge.getName());

            getAlertResponse.setName(challengeName.orElse("알수없음"));

            getAlertResponse.setType(1);
            getAlertResponse.setRequestId(c.getChallengeId());

            getAlertResponses.add(getAlertResponse);
        }

        for(Friendship f : friendships) {
            if(f.getStatus() == 0) {
                System.out.println(f.getRequestId() + "가 "+ f.getReceiveId()+ " 에게  : " + f.getStatus());
                GetAlertResponse getAlertResponse = new GetAlertResponse();

                Optional<String> friendName = userRepository.findById(f.getRequestId())
                        .map(user -> user.getName());

                getAlertResponse.setName(friendName.orElse("알수없음"));
                getAlertResponse.setType(0);
                getAlertResponse.setRequestId(f.getRequestId());

                getAlertResponses.add(getAlertResponse);
            }

        }


        return getAlertResponses;
    }

    // 알림 수락하기 (친구수락, 챌린지 수락) : FriendshipRepo, ChallengeParticipantRepo
    public void handleAlert(PostAlertResponse postAlertResponse) {
        if(postAlertResponse.getType() == 0) {
            // 친구 요청
            Long requestId = postAlertResponse.getRequest_id();
            Long receiveId = postAlertResponse.getReceive_id();

            Optional<Friendship> friendshipOptional = friendshipRepository.findByRequestIdAndReceiveId(requestId, receiveId);

            if(friendshipOptional.isPresent()) {
                Friendship friendship = friendshipOptional.get();
                if(postAlertResponse.getIs_accept()) {
                    // accept
                    System.out.println("accept");
                    updateStatusFriendById(friendship);
                } else {
                    // reject
                    System.out.println("reject");
                    friendshipRepository.deleteById(friendship.getId());
                }
            }
        } else if(postAlertResponse.getType() == 1) {
            // 챌린지 요청
            Long requestId = postAlertResponse.getRequest_id();
            Long receiveId = postAlertResponse.getReceive_id();

            Optional<ChallengeInvitation> challengeInvitationOptinal = challengeInvitationRepository.findByChallengeIdAndReceiveId(requestId, receiveId);

            if(challengeInvitationOptinal.isPresent()) {
                ChallengeInvitation challengeInvitation = challengeInvitationOptinal.get();

                if(postAlertResponse.getIs_accept()) {
                    ChallengeParticipant challengeParticipant = new ChallengeParticipant();

                    challengeParticipant.setChallengeId(requestId);
                    challengeParticipant.setUserId(receiveId);

                    challengeParticipantRepository.save(challengeParticipant);
                }

                challengeInvitationRepository.deleteById(challengeInvitation.getId());
            }
        }
    }

    // 친구 리스트 반환하기 : FriendshipRepo
    public List<GetFriendListResponse> getFriendList(Long userId) {
        List<GetFriendListResponse> getFriendListResponseList = new ArrayList<GetFriendListResponse>();

        List<Friendship> myFriendList = friendshipRepository.findMyFriends(userId);

        for(Friendship f : myFriendList) {
            Long friendId = f.getRequestId().equals(userId) ? f.getReceiveId() : f.getRequestId();

            Optional<User> userOptional = userRepository.findById(friendId);

            if(userOptional.isPresent()) {
                User user = userOptional.get();

                GetFriendListResponse getFriendListResponse = new GetFriendListResponse();

                getFriendListResponse.setName(user.getName());
                getFriendListResponse.setId(user.getId());

                getFriendListResponseList.add(getFriendListResponse);
            }
        }

        return getFriendListResponseList;
    }

    // 홈 화면 데이터 반환 : ChallengeRepo, ChallengeSuccessRepo, ChallengePositionRepo,
    public GetHomeResponse getHome(Long userId) {
        GetHomeResponse getHomeResponse = new GetHomeResponse();

        List<ChallengeSuccess> challengeSuccesses = challengeSuccessRepository.findByUserId(userId);
        List<CircleData> circleDataList = new ArrayList<>();

        List<ChallengeParticipant> challengeParticipantList = challengeParticipantRepository.findByUserId(userId);

        System.out.println("my challenge count : " + challengeParticipantList.size());

        List<HomeChallengeData> homeChallengeDataList = new ArrayList<>();

        for (ChallengeSuccess c : challengeSuccesses) {
            Optional<CircleData> existingCircleData = circleDataList.stream()
                    .filter(tempCircleData -> c.getDate().equals(tempCircleData.getDay()))
                    .findFirst();

            if (existingCircleData.isPresent()) {
                // 해당하는 circleData의 color값을 1 증가
                CircleData circleData = existingCircleData.get();
                circleData.setColor(circleData.getColor() + 1);
            } else {
                CircleData newCircleData = new CircleData();
                newCircleData.setColor(1);
                newCircleData.setDay(c.getDate());

                circleDataList.add(newCircleData);
            }
        }

        for(ChallengeParticipant c : challengeParticipantList) {
            HomeChallengeData homeChallengeData = new HomeChallengeData();

            homeChallengeData.setId(c.getChallengeId());

            // 챌린지 이름
            Optional<Challenge> challengeOptional = challengeRepository.findById(c.getChallengeId());

            if(challengeOptional.isPresent()) {
                homeChallengeData.setChallengeName(challengeOptional.get().getName());
            } else {
                homeChallengeData.setChallengeName("알수없음");
            }

            // 챌린지 위치 이름
            // 챌린지 좌표
            List<ChallengePosition> challengePositionList = challengePositionRepository.findByChallengeId(c.getChallengeId());

            List<String> dummyNameList = new ArrayList<>();
            List<Double> dummyXList = new ArrayList<>();
            List<Double> dummyYList = new ArrayList<>();

            for(ChallengePosition challengePosition : challengePositionList) {
                dummyNameList.add(challengePosition.getPositionName());
                dummyXList.add(challengePosition.getXPosition());
                dummyYList.add(challengePosition.getYPosition());
            }

            homeChallengeData.setChallengePosition(dummyNameList);
            homeChallengeData.setChallengePositionX(dummyXList);
            homeChallengeData.setChallengePositionY(dummyYList);

            // 챌린지 성공 여부

            homeChallengeData.setIsSuccess(challengeSuccessRepository.findByChallengeIdAndUserIdAndDate(c.getChallengeId(),userId, getCurrentDate()).isPresent());

            homeChallengeDataList.add(homeChallengeData);
        }

        getHomeResponse.setChallengeData(homeChallengeDataList);
        getHomeResponse.setCircleData(circleDataList);


        return getHomeResponse;
    }

    public void updateStatusFriendById(Friendship friendship) {
        // Friendship existingFriendship = friendshipRepository.findById(friendship.getId())

        // Update fields of existingFriendship
        friendship.setStatus(1);

        friendshipRepository.save(friendship);
    }
}
