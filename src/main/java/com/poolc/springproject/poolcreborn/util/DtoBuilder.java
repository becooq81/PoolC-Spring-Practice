package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.payload.response.DetailedUserDto;
import com.poolc.springproject.poolcreborn.payload.response.SimpleUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoBuilder {
    DetailedUserDto buildDetailedUserDtoFromUser(User user);
    SimpleUserDto buildSimpleUserDtoFromUser(User user);

}
