package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.model.ActivityStatus;
import com.poolc.springproject.poolcreborn.validator.PasswordMatches;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@PasswordMatches
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

    public UserUpdateRequest() {}
    public boolean passwordChanged() {
        return this.password != null && this.confirmPassword != null;
    }
}
