package com.poolc.springproject.poolcreborn.model.user;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.validator.IncludeCharInt;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
@Table(name = "USER",
        indexes = {
                @Index(name = "username", columnList = "username"),
                @Index(name = "email", columnList = "email")
        })
public class User {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "사용자 이름은 필수 입력 항목입니다.")
    @Size(min = 4, max = 12)
    @IncludeCharInt
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
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String mobileNumber;

    @NotEmpty(message = "전공은 필수 입력 항목입니다.")
    private String major;

    @NotNull(message = "학번은 필수 입력 항목입니다.")
    private int studentId;

    @NotEmpty(message = "자기소개는 필수 입력 항목입니다.")
    private String description;

    @Enumerated(EnumType.STRING)
    private SchoolStatus schoolStatus;

    private boolean isMember;
    private boolean isClubMember;
    private boolean isAdmin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Activity> leadingList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private Set<Participation> participationList = new HashSet<>();

    public User(String username, String password, String name, String email, String mobileNumber, String major, int studentId, String description) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.major = major;
        this.studentId = studentId;
        this.description = description;
        this.schoolStatus = SchoolStatus.DEFAULT;
        this.isMember = true;
        this.isClubMember = false;
        this.isAdmin = false;
    }

    public User() {
        this.isMember = true;
        this.isAdmin = false;
        this.isClubMember = false;

    }

    public void addParticipating(Activity activity) {
        this.participationList.add(new Participation(this, activity));
    }

    public int getTotalHours() {
        return this.participationList.stream()
                .map(Participation::getActivity)
                .mapToInt(Activity::getTotalHours)
                .sum();
    }

    public boolean isQualified() {
        int leadingHours = this.leadingList.stream()
                .mapToInt(Activity::getTotalHours)
                .sum();
        if (this.getTotalHours() >= 6 || this.isAdmin || leadingHours >= 4) {
            return true;
        }
        return false;
    }

}
