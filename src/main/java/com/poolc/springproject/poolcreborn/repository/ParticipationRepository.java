package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.participation.Participation;
import com.poolc.springproject.poolcreborn.model.participation.ParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipationRepository extends JpaRepository<Participation, ParticipationId> {

}
