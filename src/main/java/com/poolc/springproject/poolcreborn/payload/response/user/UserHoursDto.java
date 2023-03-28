package com.poolc.springproject.poolcreborn.payload.response.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserHoursDto {
    private String username;
    private String name;
    private String major;
    private int studentId;
    private int seminarLeadingHours;
    private int studyLeadingHours;
    private int attendingHours;
    private boolean isAdmin;
    private boolean isQualified;

    public int getTotalHours() {
        return seminarLeadingHours + studyLeadingHours + attendingHours;
    }

}
