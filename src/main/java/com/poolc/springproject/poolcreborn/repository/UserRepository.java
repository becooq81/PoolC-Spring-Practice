package com.poolc.springproject.poolcreborn.repository;

import com.poolc.springproject.poolcreborn.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    List<User> findAll();
    Page<User> findByUsernameContaining(String infix, Pageable pageable);
    Page<User> findByNameContaining(String infix, Pageable pageable);
    Page<User> findByMajorContaining(String infix, Pageable pageable);
    Page<User> findByIsClubMemberTrue(Pageable pageable);
    Page<User> findByIsAdminTrue(Pageable pageable);
}
