package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.activity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findById(Long id);

    Optional<Activity> findByTitle(String title);

}
