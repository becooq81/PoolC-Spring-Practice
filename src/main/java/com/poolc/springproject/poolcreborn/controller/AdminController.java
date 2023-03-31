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

import static com.poolc.springproject.poolcreborn.util.Message.SUCCESSFUL_ROLE_ADD;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<List<DetailedUserDto>> admin(@RequestParam int page, @RequestParam int size) {
        List<DetailedUserDto> detailedUserDtos = userService.findAllUsersByAdmin(page, size);
        return new ResponseEntity<>(detailedUserDtos, HttpStatus.OK);
    }

    @PatchMapping("/roles")
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
        return ResponseEntity.ok(SUCCESSFUL_ROLE_ADD);
    }

    @GetMapping("/hours")
    public ResponseEntity<List<UserHoursDto>> viewHours(@RequestParam int page, @RequestParam int size) {
        List<UserHoursDto> userHoursDtos = userService.findAllHoursByAdmin(page, size);
        return new ResponseEntity<>(userHoursDtos, HttpStatus.OK);
    }

}
