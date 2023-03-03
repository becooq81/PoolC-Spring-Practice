package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserDeleteRequest;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static com.poolc.springproject.poolcreborn.security.SecurityUtil.getLoginUsername;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @PatchMapping("/my-info")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        String username = getLoginUsername();

        Optional<User> optionalUser =  userRepository.findByUsername(username);
        User user = optionalUser.get();
        System.out.println(userUpdateRequest.getDescription());

        userMapper.updateUserInfoFromRequest(userUpdateRequest, user);
        System.out.println("맵핑 후: "+user.getDescription());

        userRepository.save(user);
        System.out.println("저장 후: " + user.getDescription());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }



}
