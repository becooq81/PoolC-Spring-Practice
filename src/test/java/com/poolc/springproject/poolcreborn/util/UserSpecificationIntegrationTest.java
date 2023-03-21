package com.poolc.springproject.poolcreborn.util;

import com.poolc.springproject.poolcreborn.model.search.SearchCriteria;
import com.poolc.springproject.poolcreborn.model.user.User;
import com.poolc.springproject.poolcreborn.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;


import java.util.List;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserSpecificationIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private User user1;
    private User user2;

    @Before
    public void init() {
        user1 = new User(
                "user1",
                "hello12345",
                "김사라",
                "user1@gmail.com",
                "010-5842-3912",
                "경제학과",
                201723912,
                "안녕하세요"
                );
        user1.setPassword(passwordEncoder.encode(user1.getPassword()));
        userRepository.save(user1);

        user2 = new User(
                "user1",
                "hello12345",
                "김진국",
                "user2@gmail.com",
                "010-1932-5231",
                "경제학과",
                201622312,
                "안녕하세요"
        );
        user2.setPassword(passwordEncoder.encode(user2.getPassword()));
        userRepository.save(user2);

    }
    @Test
    public void match_username() {
        UserSpecification spec =
                new UserSpecification(new SearchCriteria("username", ":", "user1"));

        List<User> results = userRepository.findAll(spec);

        assertThat(user1, isIn(results));
    }
    @Test
    public void match_email() {
        UserSpecification spec =
                new UserSpecification(new SearchCriteria("email", ":", "user2@gmail.com"));

        List<User> results = userRepository.findAll(spec);
        assertThat(user2, isIn(results));
    }

    @Test
    public void match_major() {
        UserSpecification spec =
                new UserSpecification(new SearchCriteria("major", ":", "경제학과"));

        List<User> results = userRepository.findAll(spec);
        assertThat(user1, isIn(results));
        assertThat(user2, isIn(results));
    }

}