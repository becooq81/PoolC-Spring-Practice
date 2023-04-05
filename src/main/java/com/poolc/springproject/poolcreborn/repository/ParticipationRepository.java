package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
    Boolean existsByActivityAndUser(Activity activity, User user);
    List<Participation> findByActivityTitle(String activityTitle);
    Optional<Participation> findByUserAndActivity(User user, Activity activity);
    List<Participation> findByActivityTitleAndIsApproved(String activityTitle, boolean isApproved);

}
