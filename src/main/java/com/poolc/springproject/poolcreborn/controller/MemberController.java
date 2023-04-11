package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.search.SearchRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserDeleteRequest;
import com.poolc.springproject.poolcreborn.payload.response.user.UserRoleDto;
import com.poolc.springproject.poolcreborn.payload.response.user.UserDto;
import com.poolc.springproject.poolcreborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import java.util.List;

import static com.poolc.springproject.poolcreborn.security.SecurityUtil.getLoginUsername;
import com.poolc.springproject.poolcreborn.util.Message;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final UserService userService;

    @GetMapping("/members")
    public ResponseEntity<List<UserRoleDto>> findAllUsers(@RequestParam @Positive int page,
                                                          @RequestParam @Positive int size) {
        List<UserRoleDto> userDtoList = userService.findAllUsersByClubMember(page, size);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/member/{username}")
    public ResponseEntity<UserDto> viewUser(@PathVariable("username") String username) {
        UserDto userDto = userService.findUserByClubMember(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PatchMapping("/my-info")
    public ResponseEntity<User> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        String username = getLoginUsername();
        User user = userService.updateUser(userUpdateRequest, username);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/my-info")
    public ResponseEntity<?> deleteUser(@Valid @RequestBody UserDeleteRequest userDeleteRequest) {
        String username = getLoginUsername();
        userService.deleteUser(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(Message.SUCCESSFUL_DELETE_USER);
    }

    @GetMapping("/members/search")
    public ResponseEntity<List<UserRoleDto>> searchUser(@Valid @RequestBody SearchRequest searchRequest,
                                                        @RequestParam @Positive int page,
                                                        @RequestParam @Positive int size) {
        List<UserRoleDto> users = userService.searchUser(searchRequest, page, size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}
