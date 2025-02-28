package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.payload.request.user.UserVo;
import com.poolc.springproject.poolcreborn.payload.response.user.DetailedUserDto;
import com.poolc.springproject.poolcreborn.payload.response.user.UserHoursDto;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import com.poolc.springproject.poolcreborn.util.Message;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<DetailedUserDto>> admin(@RequestParam int page, @RequestParam int size) {
        List<DetailedUserDto> detailedUserDtoList = userService.findAllUsersByAdmin(page, size);
        return new ResponseEntity<>(detailedUserDtoList, HttpStatus.OK);
    }

    @PatchMapping("/roles")
    public ResponseEntity<?> addRoles(@Valid @RequestBody List<UserVo> userVos) {
        try {
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
            return ResponseEntity.status(HttpStatus.OK)
                    .body(Message.SUCCESSFUL_ROLE_ADD);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/hours")
    public ResponseEntity<List<UserHoursDto>> viewHours(@RequestParam int page, @RequestParam int size) {
        List<UserHoursDto> userHoursDtoList = userService.findAllHoursByAdmin(page, size);
        return new ResponseEntity<>(userHoursDtoList, HttpStatus.OK);
    }

}
