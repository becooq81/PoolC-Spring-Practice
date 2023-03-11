package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUsername(String username);

}
