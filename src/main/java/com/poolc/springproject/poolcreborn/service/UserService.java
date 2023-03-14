package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.ERole;
import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.user.LoginRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.response.JwtResponse;
import com.poolc.springproject.poolcreborn.payload.response.UserDto;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.security.jwt.JwtUtils;
import com.poolc.springproject.poolcreborn.security.service.UserDetailsImpl;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtResponse(token, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public void saveUser(SignupRequest signupRequest) {
        User user = new User();
        userMapper.buildUserFromRequest(signupRequest, user);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
    }

    public User updateUserInfo(UserUpdateRequest userUpdateRequest, String currentUsername) {
        Optional<User> optionalUser =  userRepository.findByUsername(currentUsername);
        User user = optionalUser.get();
        userMapper.updateUserInfoFromRequest(userUpdateRequest, user);
        return user;
    }

    public void deleteUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        userRepository.deleteById(user.get().getId());
    }

    public void addAdminRole(String username) {
        User user = userRepository.findByUsername(username).get();
        user.setAdmin(true);
    }

    public void addClubMemberRole(String username) {
        User user = userRepository.findByUsername(username).get();
        user.setClubMember(true);
    }

    public List<UserDto> findAllUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        if (users.size() == 0) {
            return null;
        }
        for (User user : users) {
            UserDto userDto = new UserDto();
            userMapper.buildUserDtoFromUser(user, userDto);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    public UserDto findUser(String username) {
        User user = userRepository.findByUsername(username).get();
        UserDto userDto = new UserDto();
        userMapper.buildUserDtoFromUser(user, userDto);
        return userDto;
    }


}
