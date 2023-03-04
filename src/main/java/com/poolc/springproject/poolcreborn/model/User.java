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
    @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
    @Size(min = 4, max = 12)
    @IncludeCharInt()
    private String username;

    @NotEmpty(message = "암호는 필수 입력 항목입니다.")
    @Size(min = 8)
    private String password;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @Email
    @NotEmpty(message = "이메일은 필수 입력 항목입니다.")
    private String email;

    @NotEmpty(message = "전화번호 필수 입력 항목입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "전화번호는 01X-XXXX-XXXX 포맷에 맞춰 입력해 주시기 바랍니다.")
    private String mobileNumber;

    @NotEmpty(message = "전공은 필수 입력 항목입니다.")
    private String major;

    @NotNull(message = "학번은 필수 입력 항목입니다.")
    private int studentId;

    @NotEmpty(message = "자기소개는 필수 입력 항목입니다.")
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
