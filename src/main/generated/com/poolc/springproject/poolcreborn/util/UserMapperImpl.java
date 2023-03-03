package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.User;
import com.poolc.springproject.poolcreborn.payload.request.UserUpdateRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-04T00:32:10+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public void updateUserInfoFromRequest(UserUpdateRequest userUpdateRequest, User user) {
        if ( userUpdateRequest == null ) {
            return;
        }

        if ( userUpdateRequest.getPassword() != null ) {
            user.setPassword( userUpdateRequest.getPassword() );
        }
        if ( userUpdateRequest.getEmail() != null ) {
            user.setEmail( userUpdateRequest.getEmail() );
        }
        if ( userUpdateRequest.getMobileNumber() != null ) {
            user.setMobileNumber( userUpdateRequest.getMobileNumber() );
        }
        if ( userUpdateRequest.getDescription() != null ) {
            user.setDescription( userUpdateRequest.getDescription() );
        }
        if ( userUpdateRequest.getActivityStatus() != null ) {
            user.setActivityStatus( userUpdateRequest.getActivityStatus() );
        }
    }
}
