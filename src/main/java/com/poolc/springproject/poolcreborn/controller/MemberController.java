package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserDeleteRequest;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.UserService;
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
    private final UserService userService;

    @PatchMapping("/my-info")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        String username = getLoginUsername();
        User user = userService.updateUserInfo(userUpdateRequest, username);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/my-info")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDeleteRequest userDeleteRequest) {
        String username = getLoginUsername();
        userService.deleteUser(username);
        return ResponseEntity.ok("The user is successfully deleted.");
    }

}
