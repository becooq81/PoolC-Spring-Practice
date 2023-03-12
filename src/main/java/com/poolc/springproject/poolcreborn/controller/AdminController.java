package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.payload.request.user.UserVo;
import com.poolc.springproject.poolcreborn.payload.response.UserDto;
import com.poolc.springproject.poolcreborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDto>> admin() {
        List<UserDto> userDtos = userService.findAllUsers();
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PatchMapping("/add")
    public ResponseEntity<?> addRoles(@Valid @RequestBody List<UserVo> userVos) {
        for (UserVo userVo : userVos) {
            String username = userVo.getUsername();
            if (userVo.isAdmin()) {
                userService.addAdminRole(username);
            }
            if (userVo.isClubMember()) {
                userService.addClubMemberRole(username);
            }
        }
        return ResponseEntity.ok("Roles have been modified successfully.");
    }
}
