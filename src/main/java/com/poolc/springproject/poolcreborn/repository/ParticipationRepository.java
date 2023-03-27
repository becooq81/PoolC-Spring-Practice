package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.participation.Participation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, Long> {
}
