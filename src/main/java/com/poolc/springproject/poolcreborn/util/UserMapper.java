package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.user.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.response.user.DetailedUserDto;
import com.poolc.springproject.poolcreborn.payload.response.user.SimpleUserMajorDto;
import com.poolc.springproject.poolcreborn.payload.response.user.SimpleUserRoleDto;
import com.poolc.springproject.poolcreborn.payload.response.user.UserDto;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserInfoFromRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildUserFromRequest(SignupRequest signupRequest, @MappingTarget User user);
    DetailedUserDto buildDetailedUserDtoFromUser(User user);
    SimpleUserRoleDto buildSimpleUserRoleDtoFromUser(User user);
    SimpleUserMajorDto buildSimpleUserMajorDtoFromUser(User user);
    UserDto buildUserDtoFromUser(User user);
}