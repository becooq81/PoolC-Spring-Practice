package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import org.mapstruct.*;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserInfoFromRequest(UserUpdateRequest userUpdateRequest, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildUserFromRequest(SignupRequest signupRequest, @MappingTarget User user);

}