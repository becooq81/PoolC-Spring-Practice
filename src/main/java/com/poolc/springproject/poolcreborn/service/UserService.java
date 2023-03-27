package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.model.search.SearchCategory;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.search.SearchRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.LoginRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.response.DetailedUserDto;
import com.poolc.springproject.poolcreborn.payload.response.JwtResponse;
import com.poolc.springproject.poolcreborn.payload.response.SimpleUserDto;
import com.poolc.springproject.poolcreborn.payload.response.UserDto;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.security.jwt.JwtUtils;
import com.poolc.springproject.poolcreborn.security.service.UserDetailsImpl;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<DetailedUserDto> findAllUsersByAdmin(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pr);
        if (users.getNumberOfElements() == 0) {
            return null;
        }
        return users.stream()
                .map(u -> userMapper.buildDetailedUserDtoFromUser(u))
                .collect(Collectors.toList());
    }

    public List<SimpleUserDto> findAllUsersByClubMember(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pr);
        if (users.getNumberOfElements() == 0) {
            return new ArrayList<>();
        }
        return users.stream()
                .filter(User::isClubMember)
                .map(u -> userMapper.buildSimpleUserDtoFromUser(u))
                .collect(Collectors.toList());
    }

    public UserDto findUserByClubMember(String username) {
        User user = userRepository.findByUsername(username).get();
        return userMapper.buildUserDtoFromUser(user);
    }
    public List<SimpleUserDto> searchUser(SearchRequest searchRequest, int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return processSearchRequest(searchRequest, pr);
    }
    private List<SimpleUserDto> processSearchRequest(SearchRequest searchRequest, PageRequest pr) {
        Page<User> searchUsers;
        String keyword = searchRequest.getKeyword();
        switch (searchRequest.getSearchCategory()) {
            case USERNAME:
                searchUsers = userRepository.findByUsernameContaining(keyword, pr);
                break;
            case NAME:
                searchUsers = userRepository.findByNameContaining(keyword, pr);
                break;
            case MAJOR:
                searchUsers = userRepository.findByMajorContaining(keyword, pr);
                break;
            case ISADMIN:
                searchUsers = userRepository.findByIsAdminTrue(pr);
                break;
            case ISCLUBMEMBER:
                searchUsers = userRepository.findByIsClubMemberTrue(pr);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + searchRequest.getSearchCategory());
        }
        return searchUsers.stream().map(u -> userMapper.buildSimpleUserDtoFromUser(u)).collect(Collectors.toList());

    }
}
