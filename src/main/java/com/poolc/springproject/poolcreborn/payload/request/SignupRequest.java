package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.validator.IncludeCharInt;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.*;

@Data
public class SignupRequest {

    @IncludeCharInt
    @Size(min = 4, max = 12)
    @NotBlank
    private String username;

    @Length(min = 8)
    private String password;

    @Length(min = 8)
    @Transient
    private String confirmPassword;

    @NotBlank
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String mobileNumber;

    @NotEmpty
    private String major;

    @NotNull
    private int studentId;

    @NotEmpty
    private String description;

}
