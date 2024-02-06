package com.parkyounbae.challengehere.service;

import com.parkyounbae.challengehere.domain.challenge.ChallengeSuccess;
import com.parkyounbae.challengehere.domain.user.Friendship;
import com.parkyounbae.challengehere.domain.user.User;
import com.parkyounbae.challengehere.dto.friend.CommitData;
import com.parkyounbae.challengehere.dto.friend.GetFriendCommitDataResponse;
import com.parkyounbae.challengehere.dto.friend.GetFriendSearchResultResponse;
import com.parkyounbae.challengehere.dto.home.CircleData;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeRepository;
import com.parkyounbae.challengehere.repository.interfaces.challenge.ChallengeSuccessRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.FriendshipRepository;
import com.parkyounbae.challengehere.repository.interfaces.user.UserRepository;
//import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class FriendService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeSuccessRepository challengeSuccessRepository;

    @Autowired
    public FriendService(FriendshipRepository friendshipRepository, UserRepository userRepository, ChallengeRepository challengeRepository, ChallengeSuccessRepository challengeSuccessRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
        this.challengeRepository = challengeRepository;
        this.challengeSuccessRepository = challengeSuccessRepository;
    }

    // 나의 친구 커밋데이터 불러오기 : FriendshipRepo, UserRepo, ChallengeSuccess
    public List<GetFriendCommitDataResponse> getFriendCommitData(Long userId) {
        List<GetFriendCommitDataResponse> getFriendCommitDataResponses = new ArrayList<>();

        List<Friendship> myFriends = friendshipRepository.findMyFriends(userId);

        for(Friendship f : myFriends) {
            GetFriendCommitDataResponse tempCommitResponseData = new GetFriendCommitDataResponse();

            Long friendId = f.getRequestId().equals(userId) ? f.getReceiveId() : f.getRequestId();

            Optional<User> tempUser = userRepository.findById(friendId);
            if(tempUser.isPresent()) {
                tempCommitResponseData.setName(tempUser.get().getName());
            } else {
                tempCommitResponseData.setName("알수없음");
            }

            // commit data
            List<ChallengeSuccess> challengeSuccesses = challengeSuccessRepository.findByUserId(friendId);
            List<CommitData> commitData = new ArrayList<>();

            for (ChallengeSuccess c : challengeSuccesses) {
                Optional<CommitData> existingCircleData = commitData.stream()
                        .filter(tempCommitData -> c.getDate().equals(tempCommitData.getDate()))
                        .findFirst();

                if (existingCircleData.isPresent()) {
                    // 해당하는 circleData의 color값을 1 증가
                    CommitData circleData = existingCircleData.get();
                    circleData.setCount(circleData.getCount() + 1);
                } else {
                    CommitData newCircleData = new CommitData();
                    newCircleData.setCount(1);
                    newCircleData.setDate(c.getDate());

                    commitData.add(newCircleData);
                }
            }

            tempCommitResponseData.setCommitData(commitData);
            getFriendCommitDataResponses.add(tempCommitResponseData);
        }

        return getFriendCommitDataResponses;
    }


    // 친구 요청하기 : FriendshipRepo
    public void requestFriend(Long requestId, Long receiveId) {
        Friendship friendship = new Friendship();

        friendship.setStatus(0);
        friendship.setRequestId(requestId);
        friendship.setReceiveId(receiveId);

        friendshipRepository.save(friendship);
    }

    // 친구찾기
    public List<GetFriendSearchResultResponse> searchFriend(Long userId, String name) {
        List<GetFriendSearchResultResponse> getFriendSearchResultResponses = new ArrayList<>();

        List<User> users = userRepository.findAllUsersWithName(name);

        for(User u : users) {
            GetFriendSearchResultResponse getFriendSearchResultResponse = new GetFriendSearchResultResponse();

            if(friendshipRepository.findByRequestIdAndReceiveId(u.getId(), userId).isPresent() || friendshipRepository.findByRequestIdAndReceiveId(userId, u.getId()).isPresent()) {
                // 둘이 친구이거나 이미 친구 신청을 보낸 상태임
                getFriendSearchResultResponse.setId(u.getId());
                getFriendSearchResultResponse.setName(u.getName());
                getFriendSearchResultResponse.setStatus(0);
            } else {
                getFriendSearchResultResponse.setId(u.getId());
                getFriendSearchResultResponse.setName(u.getName());
                getFriendSearchResultResponse.setStatus(1);
            }

            getFriendSearchResultResponses.add(getFriendSearchResultResponse);
        }

        return getFriendSearchResultResponses;
    }
}
