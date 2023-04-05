package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.request.user.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.user.UserUpdateRequest;
import com.poolc.springproject.poolcreborn.payload.response.user.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserInfoFromRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildUserFromRequest(SignupRequest signupRequest, @MappingTarget User user);
    DetailedUserDto buildDetailedUserDtoFromUser(User user);
    UserRoleDto buildSimpleUserRoleDtoFromUser(User user);
    UserMajorDto buildSimpleUserMajorDtoFromUser(User user);
    UserDto buildUserDtoFromUser(User user);
    UserHoursDto buildUserHoursDtoFromUser(User user);
}