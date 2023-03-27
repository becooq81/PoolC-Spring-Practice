package com.poolc.springproject.poolcreborn.model.participation;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Participation {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int participationCount;

    public Participation() {}
    public Participation(User user, Activity activity) {
        this.user = user;
        this.activity = activity;
    }
}
