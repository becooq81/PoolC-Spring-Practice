package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.payload.request.user.UserVo;
import com.poolc.springproject.poolcreborn.payload.response.UserDto;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<UserDto>> admin(@RequestParam(value = "page") int page,
                                               @RequestParam(value = "size") int size) {
        List<UserDto> userDtos = userService.findAllUsersByAdmin(page, size);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @PatchMapping("/add")
    public ResponseEntity<?> addRoles(@Valid @RequestBody List<UserVo> userVos) {
        for (UserVo userVo : userVos) {
            String username = userVo.getUsername();
            if (!userRepository.existsByUsername(username)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
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
