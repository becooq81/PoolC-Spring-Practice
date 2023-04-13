package com.poolc.springproject.poolcreborn.service;

import com.poolc.springproject.poolcreborn.exception.InvalidUserException;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.search.SearchRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.LoginRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.response.user.DetailedUserDto;
import com.poolc.springproject.poolcreborn.payload.response.JwtResponse;
import com.poolc.springproject.poolcreborn.payload.response.user.UserHoursDto;
import com.poolc.springproject.poolcreborn.payload.response.user.UserRoleDto;
import com.poolc.springproject.poolcreborn.payload.response.user.UserDto;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import com.poolc.springproject.poolcreborn.security.jwt.JwtUtils;
import com.poolc.springproject.poolcreborn.security.service.UserDetailsImpl;
import com.poolc.springproject.poolcreborn.util.Message;
import com.poolc.springproject.poolcreborn.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return new JwtResponse(token, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles);
    }

    public void saveUser(SignupRequest signupRequest) {
        User user = new User();
        userMapper.buildUserFromRequest(signupRequest, user);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
    }

    public User updateUser(UserUpdateRequest userUpdateRequest, String currentUsername) throws InvalidUserException {
        User user =  userRepository.findByUsername(currentUsername)
                        .orElseThrow(() -> new InvalidUserException(Message.USER_DOES_NOT_EXIST));
        userMapper.updateUserFromRequest(userUpdateRequest, user);
        return user;
    }

    public void deleteUser(String username) throws InvalidUserException {
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new InvalidUserException(Message.USER_DOES_NOT_EXIST));
        userRepository.deleteById(user.getId());
    }

    public void addAdminRole(String username) throws InvalidUserException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidUserException(Message.USER_DOES_NOT_EXIST));
        user.setAdmin(true);
    }

    public void addClubMemberRole(String username) throws InvalidUserException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidUserException(Message.USER_DOES_NOT_EXIST));
        user.setClubMember(true);
    }

    public List<DetailedUserDto> findAllUsersByAdmin(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pr);
        if (users.getNumberOfElements() == 0) {
            return null;
        }
        return users.stream()
                .map(userMapper::buildDetailedUserDtoFromUser)
                .collect(Collectors.toList());
    }

    public List<UserRoleDto> findAllUsersByClubMember(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pr);
        if (users.getNumberOfElements() == 0) {
            return new ArrayList<>();
        }
        return users.stream()
                .filter(User::isClubMember)
                .map(userMapper::buildUserRoleDtoFromUser)
                .collect(Collectors.toList());
    }

    public UserDto findUserByClubMember(String username) throws InvalidUserException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidUserException(Message.USER_DOES_NOT_EXIST));
        return userMapper.buildUserDtoFromUser(user);
    }
    public List<UserRoleDto> searchUser(SearchRequest searchRequest, int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return processSearchRequest(searchRequest, pr);
    }
    private List<UserRoleDto> processSearchRequest(SearchRequest searchRequest, PageRequest pr) {
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
        return searchUsers.stream()
                .map(userMapper::buildUserRoleDtoFromUser)
                .collect(Collectors.toList());

    }
    public List<UserHoursDto> findAllHoursByAdmin(int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pr);
        if (users.getNumberOfElements() == 0) {
            return new ArrayList<>();
        }
        return users.stream()
                .filter(User::isClubMember)
                .map(userMapper::buildUserHoursDtoFromUser)
                .collect(Collectors.toList());
    }
}
