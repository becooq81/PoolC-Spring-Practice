package com.poolc.springproject.poolcreborn;

import com.poolc.springproject.poolcreborn.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Controller
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct // 애플리케이션 로딩 시점에 호출
    public void init() {
        initService.dbInit1();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;
        private final PasswordEncoder passwordEncoder;

        public void dbInit1() {
            User admin = new User(
                    "admin1234",
                    "admin1234",
                    "관리자",
                    "admin@gmail.com",
                    "010-2831-2312",
                    "경제학과",
                    201828281,
                    "안녕하세요"
            );
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setAdmin(true);
            em.persist(admin);

            User restricted = new User(
                    "becooq81",
                    "hello12345",
                    "지니",
                    "becooq81@gmail.com",
                    "010-3668-3828",
                    "사회복지학과",
                    2038182312,
                    "hi"
            );
            restricted.setPassword(passwordEncoder.encode(restricted.getPassword()));
            em.persist(restricted);

            User member = new User(
                    "member1234",
                    "hello12345",
                    "멤버",
                    "member@gmail.com",
                    "010-3828-3123",
                    "수학과",
                    2018273123,
                    "welcome"
            );
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            member.setClubMember(true);
            em.persist(member);
        }
    }
}
