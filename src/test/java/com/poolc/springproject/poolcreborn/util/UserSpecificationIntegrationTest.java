package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.repository.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserSpecificationIntegrationTest {

    @Autowired
    private UserRepository repository;


}