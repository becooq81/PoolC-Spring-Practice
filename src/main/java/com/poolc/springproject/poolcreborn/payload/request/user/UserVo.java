package com.poolc.springproject.poolcreborn.payload.request.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter
@ToString
@Builder(toBuilder = true)
public class UserVo {

    @NotEmpty
    private String username;

    private boolean isMember;
    private boolean isClubMember;
    private boolean isAdmin;

    public UserVo() {}
    public UserVo(String username, boolean isMember, boolean isClubMember, boolean isAdmin) {
        this.username = username;
        this.isMember = isMember;
        this.isClubMember = isClubMember;
        this.isAdmin = isAdmin;
    }

}
