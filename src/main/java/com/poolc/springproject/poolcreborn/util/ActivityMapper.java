package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityRequest;
import com.poolc.springproject.poolcreborn.payload.request.activity.ActivityUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void buildActivityFromRequest(ActivityRequest activityRequest, @MappingTarget Activity activity);

    @BeanMapping(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateActivityInfoFromRequest(ActivityUpdateRequest activityUpdateRequest, @MappingTarget Activity activity);
}
