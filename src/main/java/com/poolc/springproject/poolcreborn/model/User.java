package com.poolc.springproject.poolcreborn.model;

import com.poolc.springproject.poolcreborn.validator.IncludeCharInt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    @Size(min = 4, max = 12)
    @IncludeCharInt
    private String username;

    @NotEmpty
    @Size(min = 8)
    private String password;

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

    @Enumerated(EnumType.STRING)
    private ActivityStatus activityStatus;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password, String name, String email, String mobileNumber, String major, int studentId, String description) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.major = major;
        this.studentId = studentId;
        this.description = description;
        this.activityStatus = ActivityStatus.DEFAULT;
    }

}
