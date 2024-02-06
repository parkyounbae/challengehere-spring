package com.parkyounbae.challengehere.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.parkyounbae.challengehere.domain.user.User;
import com.parkyounbae.challengehere.dto.alert.GetAlertResponse;
import com.parkyounbae.challengehere.dto.alert.PostAlertResponse;
import com.parkyounbae.challengehere.dto.challenge.GetFriendListResponse;
import com.parkyounbae.challengehere.dto.home.GetHomeResponse;
import com.parkyounbae.challengehere.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);  //1

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/create")
    public Long createUser(@RequestParam("name") String name, @RequestParam("token") String token, @RequestParam("provider") String provider) {
        System.out.println("signUp " + name + " " + token + " " + provider);
        return userService.signUp(name, token, provider);
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> loginUser(@RequestParam("token") String token, @RequestParam("provider") String provider) {
        logger.info("login requet : "+token+" " + provider);

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                .setAudience(Collections.singletonList("239355814036-ftfq0rad8qgag9qpfj31bam31utbgv3q.apps.googleusercontent.com"))
                .build();

        GoogleIdToken idToken = null;
        String userGoogleId = "";
        try {
            idToken = verifier.verify(token);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                userGoogleId = payload.getSubject();
                System.out.println("User ID: " + userGoogleId);

            } else {
                System.out.println("Invalid ID token.");
            }

        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<User> user = userService.login(userGoogleId, provider);

        if(user.isPresent()) {
            logger.info("exist");
            return new ResponseEntity<>(user.get().getId().toString(), HttpStatus.OK);
            // return user.get().getId();
        } else {
            logger.info("no exist");
            return new ResponseEntity<>(userGoogleId, HttpStatus.NOT_FOUND);
            // return 0L;
        }
    }

    @DeleteMapping("/user")
    public void deleteUser(@RequestHeader String user_id) {
        userService.deleteUser(Long.parseLong(user_id));
    }

    @GetMapping("/user/name")
    public Boolean checkDuplicate(@RequestHeader String name) {
        return userService.checkDuplicate(name);
    }

    @GetMapping("/user/alert")
    public List<GetAlertResponse> getAlert(@RequestHeader String user_id) {
        List<GetAlertResponse> temp = new ArrayList<>();
        temp = userService.getAlertList(Long.parseLong(user_id));
        System.out.println("alert count : " + temp.size());
        return temp;
    }

    @PostMapping("/user/alert")
    public ResponseEntity<Void> handleAlert(@RequestParam String receive_id, @RequestParam String request_id, @RequestParam int type, @RequestParam Boolean is_accept) {
        PostAlertResponse postAlertResponse = new PostAlertResponse();
        postAlertResponse.setReceive_id(Long.parseLong(receive_id));
        postAlertResponse.setRequest_id(Long.parseLong(request_id));
        postAlertResponse.setType(type);
        postAlertResponse.setIs_accept(is_accept);

        userService.handleAlert(postAlertResponse);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/user/friendList")
    public ResponseEntity<List<GetFriendListResponse>> getFriendList(@RequestHeader String user_id) {
        List<GetFriendListResponse> getFriendListResponseList = userService.getFriendList(Long.parseLong(user_id));

        return new ResponseEntity(getFriendListResponseList,HttpStatus.OK);
    }

    @GetMapping("/user/home")
    public ResponseEntity<GetHomeResponse> getHome(@RequestHeader String user_id) {
        GetHomeResponse getHomeResponse = userService.getHome(Long.parseLong(user_id));

        return new ResponseEntity(getHomeResponse, HttpStatus.OK);
    }
}
