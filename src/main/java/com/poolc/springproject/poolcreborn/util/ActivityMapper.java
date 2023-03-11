package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.Activity;
import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.SignupRequest;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildActivityFromRequest(ActivityRequest activityRequest, @MappingTarget Activity activity);

}