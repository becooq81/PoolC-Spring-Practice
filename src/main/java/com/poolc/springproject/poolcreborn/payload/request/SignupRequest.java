package com.poolc.springproject.poolcreborn.payload.request;

import com.poolc.springproject.poolcreborn.validator.IncludeCharInt;
import com.poolc.springproject.poolcreborn.validator.PasswordMatches;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Transient;
import javax.validation.constraints.*;

@Setter
@ToString
@PasswordMatches
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

    public SignupRequest() {}

    public SignupRequest(String username, String password, String confirmPassword, String name, String email, String mobileNumber, String major, int studentId, String description) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.major = major;
        this.studentId = studentId;
        this.description = description;
    }
}
