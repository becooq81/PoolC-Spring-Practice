package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.Activity;
import com.poolc.springproject.poolcreborn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUser(User user);
    Optional<Activity> findById(Long id);

}
