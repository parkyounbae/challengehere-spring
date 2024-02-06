package com.parkyounbae.challengehere.service;

import com.parkyounbae.challengehere.domain.challenge.*;
import com.parkyounbae.challengehere.domain.post.Post;
import com.parkyounbae.challengehere.domain.user.User;
import com.parkyounbae.challengehere.dto.challenge.*;
import com.parkyounbae.challengehere.dto.home.CircleData;
import com.parkyounbae.challengehere.dto.home.GetHomeResponse;
import com.parkyounbae.challengehere.dto.home.HomeChallengeData;
import com.parkyounbae.challengehere.dto.home.PostValidateChallengeRequest;
import com.parkyounbae.challengehere.repository.interfaces.challenge.*;
import com.parkyounbae.challengehere.repository.interfaces.post.PostRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.FriendshipRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.UserRepository;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.tomcat.util.http.FastHttpDateFormat.getCurrentDate;

@Service
//@Transactional
public class ChallengeService {
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeSuccessRepository challengeSuccessRepository;
    private final ChallengeInvitationRepository challengeInvitationRepository;
    private final ChallengeParticipantRepository challengeParticipantRepository;
    private final ChallengePositionRepository challengePositionRepository;
    private final PostRepository postRepository;
    private final DailyUpdateService dailyUpdateService;

    @Autowired
    public ChallengeService(UserRepository userRepository,
                            FriendshipRepository friendshipRepository,
                            ChallengeRepository challengeRepository,
                            ChallengeSuccessRepository challengeSuccessRepository,
                            ChallengeInvitationRepository challengeInvitationRepository,
                            ChallengeParticipantRepository challengeParticipantRepository,
                            ChallengePositionRepository challengePositionRepository,
                            PostRepository postRepository,
                            DailyUpdateService dailyUpdateService
    ) {
        this.userRepository = userRepository;
        this.challengeInvitationRepository = challengeInvitationRepository;
        this.challengeParticipantRepository = challengeParticipantRepository;
        this.challengeRepository = challengeRepository;
        this.challengeSuccessRepository = challengeSuccessRepository;
        this.challengePositionRepository = challengePositionRepository;
        this.postRepository = postRepository;
        this.dailyUpdateService = dailyUpdateService;
    }

    public void modifyChallengeNotification(Long challengeId, String content) {
        Optional<Challenge> challengeOptional = challengeRepository.findById(challengeId);

        if(challengeOptional.isPresent()) {
            Challenge challenge = challengeOptional.get();

            challenge.setNotification(content);

            challengeRepository.save(challenge);
        }
    }

    // 챌린지 생성하기 : ChallengeRepo, ChallengeInvitationRepo, ChallengeParticipantRepo, ChallengePositionRepo
    public void addChallenge(PostAddChallengeRequest postAddChallengeRequest) {
        // ChallengeRepo
        Challenge challenge = new Challenge();
        challenge.setCoverPath(postAddChallengeRequest.getCover());
        challenge.setName(postAddChallengeRequest.getName());
        challenge.setPower(0.0);
        challenge.setNotification("공지사항");
        Challenge newChallenge = challengeRepository.save(challenge);

        // ChallengeInvitationRepo
        for (Participant p : postAddChallengeRequest.getParticipant()) {
            ChallengeInvitation challengeInvitation = new ChallengeInvitation();

            challengeInvitation.setChallengeId(newChallenge.getId());
            challengeInvitation.setReceiveId(p.getId());
            challengeInvitation.setStatus(0);

            challengeInvitationRepository.save(challengeInvitation);
        }

        // ChallengeParticipant
        ChallengeParticipant challengeParticipant = new ChallengeParticipant();
        challengeParticipant.setUserId(postAddChallengeRequest.getUserId());
        challengeParticipant.setChallengeId(newChallenge.getId());
        challengeParticipantRepository.save(challengeParticipant);

        // ChallengePosition
        for (Position p : postAddChallengeRequest.getPosition()) {
            ChallengePosition position = new ChallengePosition();
            position.setChallengeId(newChallenge.getId());
            position.setPositionName(p.getName());
            position.setXPosition(p.getX());
            position.setYPosition(p.getY());

            challengePositionRepository.save(position);
        }
    }

    // 챌린지 리스트 반환하기 : ChallengeSuccessRepo, UserRepo
    public List<GetChallengeListResponse> getChallengeListResponse(Long userId) {
        List<GetChallengeListResponse> getChallengeListResponseList = new ArrayList<>();

        List<ChallengeParticipant> myChallenge = challengeParticipantRepository.findBuUserId(userId);

        for (ChallengeParticipant c : myChallenge) {
            GetChallengeListResponse temp = new GetChallengeListResponse();

            // 챌린지 객체 불러오기
            Optional<Challenge> findChallenge = challengeRepository.findById(c.getChallengeId());

            // 해당 챌린지에 참여한 사람들 이름 반환
            List<ChallengeParticipant> findParticipant = challengeParticipantRepository.findByChallengeId(c.getChallengeId());
            List<String> findUserName = new ArrayList<>();
            for (ChallengeParticipant challengeParticipant : findParticipant) {
                Optional<User> tempUser = userRepository.findById(challengeParticipant.getUserId());
                if (tempUser.isPresent()) {
                    findUserName.add(tempUser.get().getName());
                }
            }

            // 챌린지에 해당하는 성공 데이터
            List<ChallengeSuccess> challengeSuccesseList = challengeSuccessRepository.findByUserIdAndChallengeId(userId, c.getChallengeId());
            List<String> successStringData = new ArrayList<>();
            for (ChallengeSuccess challengeSuccess : challengeSuccesseList) {
                successStringData.add(challengeSuccess.getDate());
            }

            temp.setId(c.getChallengeId());
            temp.setPerson(findUserName);
            temp.setName(findChallenge.get().getName());
            temp.setSuccessDate(successStringData);
            getChallengeListResponseList.add(temp);
        }

        return getChallengeListResponseList;
    }

    // 챌린지 디테일 반환하기 : PostRepo
    public GetChallengeResponse getChallengeResponse(Long challengeId) {
        GetChallengeResponse challengeResponse = new GetChallengeResponse();

        Optional<Challenge> challengeOptional = challengeRepository.findById(challengeId);
        if (!challengeOptional.isPresent()) {
            // error
        }
        Challenge challenge = challengeOptional.get();

//        private String coverURL;
        challengeResponse.setCoverURL(challenge.getCoverPath());

//        private String notification;
        challengeResponse.setNotification(challenge.getNotification());

//        private Double challengePower;
        challengeResponse.setChallengePower(challengeResponse.getChallengePower());

//        private List<String> todaySuccessParticipant;
        List<Long> todaySuccessUserIdList = challengeSuccessRepository.findByChallengeIdAndDate(challengeId, dailyUpdateService.getCurrentDateString());
        List<String> todaySuccessNameList = new ArrayList<>();

        for (Long id : todaySuccessUserIdList) {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                todaySuccessNameList.add(user.get().getName());
            }
        }
        challengeResponse.setTodaySuccessParticipant(todaySuccessNameList);

//        private List<CircleData> colorData;
        List<ChallengeSuccess> challengeSuccesses = challengeSuccessRepository.findByChallengeId(challengeId);
        List<CircleData> circleDataList = new ArrayList<>();

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
        challengeResponse.setColorData(circleDataList);

        //private List<RankData> rankData;
        List<RankData> rankDataList = new ArrayList<>();
        // 위에서 불러온 challengeSuccesses 에서 userId 종류별로 몇개 있는지 합산한뒤 개수가 많은 순으로 정렬하고 싶어
        // 이후 rankDataList에 (이름, 개수) 정보를 넣고 싶어
        // challengeSuccesses에는 userId만 있기 때문에 이걸 userRepository.findById(userId).get().getName()을 통해 이름을 알아내야 함
        // ChallengeSuccess를 userId로 그룹화하고 개수를 집계합니다.
        Map<Long, Long> userSuccessCount = challengeSuccesses.stream()
                .collect(Collectors.groupingBy(ChallengeSuccess::getUserId, Collectors.counting()));

        // 개수가 많은 순으로 정렬합니다.
        List<Map.Entry<Long, Long>> sortedUserSuccessCount = userSuccessCount.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toList());

        // 상위 10명의 RankData를 생성합니다.
        int maxRankCount = Math.min(sortedUserSuccessCount.size(), 10);
        for (int i = 0; i < maxRankCount; i++) {
            Map.Entry<Long, Long> entry = sortedUserSuccessCount.get(i);
            Long userId = entry.getKey();
            Long successCount = entry.getValue();

            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                RankData rankData = new RankData();
                rankData.setName(user.getName());
                rankData.setCount(successCount.intValue());
                rankDataList.add(rankData);
            }
        }

        challengeResponse.setRankData(rankDataList);


        //  private List<ChallengeBoardThumbnailData> boardThumbnail;
        List<ChallengeBoardThumbnailData> thumbnailDataList = new ArrayList<>();
        List<Post> postList = postRepository.findByChallengeId(challengeId);

        // Post 개수 제한
        int maxPostCount = Math.min(postList.size(), 3);
        for (int i = 0; i < maxPostCount; i++) {
            Post post = postList.get(i);

            // ChallengeBoardThumbnailData를 생성하고 정보를 설정하여 thumbnailDataList에 추가
            ChallengeBoardThumbnailData thumbnailData = new ChallengeBoardThumbnailData();

            Optional<User> postWriterUser = userRepository.findById(post.getUserId());

            if(postWriterUser.isPresent()) {
                thumbnailData.setName(postWriterUser.get().getName());
            } else {
                thumbnailData.setName("알수없음");
            }

            thumbnailData.setContent(post.getContent());
            thumbnailDataList.add(thumbnailData);
        }

        challengeResponse.setBoardThumbnail(thumbnailDataList);
        challengeResponse.setId(challengeId);

        // challenge Power
        challengeResponse.setChallengePower(0.3);

        return challengeResponse;
    }

    // 챌린지 인증하기
    public GetHomeResponse validateChallenge(PostValidateChallengeRequest postValidateChallengeRequest) {
        Long challenge_id = Long.parseLong(postValidateChallengeRequest.getChallenge_id());
        Long userId = Long.parseLong(postValidateChallengeRequest.getUser_id());


        ChallengeSuccess challengeSuccess = new ChallengeSuccess();
        challengeSuccess.setChallengeId(challenge_id);
        challengeSuccess.setUserId(userId);
        challengeSuccess.setDate(dailyUpdateService.getCurrentDateString());

        challengeSuccessRepository.save(challengeSuccess);

        // Long userId = postValidateChallengeRequest.getUser_id();
        System.out.println(dailyUpdateService.getCurrentDateString());
        System.out.println(challengeSuccess.getDate());

        GetHomeResponse getHomeResponse = new GetHomeResponse();

        List<ChallengeSuccess> challengeSuccesses = challengeSuccessRepository.findByUserId(userId);
        List<CircleData> circleDataList = new ArrayList<>();

        List<ChallengeParticipant> challengeParticipantList = challengeParticipantRepository.findBuUserId(userId);

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

            homeChallengeData.setIsSuccess(challengeSuccessRepository.findByChallengeIdAndUserIdAndDate(c.getChallengeId(),userId, dailyUpdateService.getCurrentDateString()));

            homeChallengeDataList.add(homeChallengeData);
        }

        getHomeResponse.setChallengeData(homeChallengeDataList);
        getHomeResponse.setCircleData(circleDataList);


        return getHomeResponse;


    }


}
