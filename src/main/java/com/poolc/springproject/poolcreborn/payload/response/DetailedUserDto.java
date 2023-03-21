package com.poolc.springproject.poolcreborn.payload.response;

import com.poolc.springproject.poolcreborn.model.user.SchoolStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetailedUserDto {

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
