package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.model.participation.RequestedParticipation;
import com.poolc.springproject.poolcreborn.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestedParticipationRepository extends JpaRepository<RequestedParticipation, Long> {
    List<RequestedParticipation> findAll();

    List<RequestedParticipation> findByActivityTitle(String activityTitle);

    Boolean existsByActivityTitleAndUsername(String username, String activityTitle);


}
