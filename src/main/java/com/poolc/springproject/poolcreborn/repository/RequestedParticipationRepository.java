package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.participation.RequestedParticipation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestedParticipationRepository extends JpaRepository<RequestedParticipation, Long> {
    List<RequestedParticipation> findAll();

    List<RequestedParticipation> findByActivityTitle(String activityTitle);


}
