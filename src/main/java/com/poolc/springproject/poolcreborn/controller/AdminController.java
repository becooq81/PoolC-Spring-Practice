package com.poolc.springproject.poolcreborn.controller;

import com.poolc.springproject.poolcreborn.payload.request.UserVo;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PatchMapping("/add")
    public ResponseEntity<?> addAdmins(@Valid @RequestBody List<UserVo> userVos) {
        for (UserVo userVo : userVos) {
            String username = userVo.getUsername();
            if (userVo.isAdmin()) {
                userService.addAdminRole(username);
            }
            if (userVo.isMember()) {
                userService.addMemberRole(username);
            }
        }
        return ResponseEntity.ok("Roles have been modified successfully.");
    }
}
