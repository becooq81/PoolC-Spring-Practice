package com.poolc.springproject.poolcreborn.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole role;

    @OneToMany(mappedBy = "role")
    private Set<UserRole> users = new HashSet<>();

}
