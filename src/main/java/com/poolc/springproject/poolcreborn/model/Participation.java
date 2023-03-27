package com.poolc.springproject.poolcreborn.model;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.user.User;

import javax.persistence.*;

@Entity
@IdClass(Participation.class)
public class Participation {
    @Id
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
