package com.poolc.springproject.poolcreborn.payload.response;

import com.poolc.springproject.poolcreborn.model.SchoolStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String name;
    private String email;
    private String mobileNumber;
    private String major;
    private int studentId;
    private SchoolStatus schoolStatus;
    private boolean isMember;
    private boolean isClubMember;
    private boolean isAdmin;

}
