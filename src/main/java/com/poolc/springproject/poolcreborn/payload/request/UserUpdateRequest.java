package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.model.ActivityStatus;
import com.poolc.springproject.poolcreborn.validator.PasswordMatches;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@ToString
@PasswordMatches
@Builder(toBuilder = true)
public class UserUpdateRequest {
    @Size(min = 8)
    private String password;

    @Size(min = 8)
    @Transient
    private String confirmPassword;

    @Email
    private String email;

    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String mobileNumber;

    private String description;

    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    public boolean passwordChanged() {
        return this.password != null && this.confirmPassword != null;
    }

    public UserUpdateRequest(String password, String confirmPassword, String email, String mobileNumber, String description, ActivityStatus activityStatus) {
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.description = description;
        this.activityStatus = activityStatus;
    }

    public UserUpdateRequest() {}
}
