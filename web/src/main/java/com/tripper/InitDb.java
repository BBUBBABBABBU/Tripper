package com.tripper;

import com.tripper.dto.request.board.CreateBoardDto;
import com.tripper.domain.board.Board;
import com.tripper.domain.user.Role;
import com.tripper.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * @author HanJiyoung
 * 스프링 부트 실행시 조회용 샘플 데이터를 넣어주는 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
//        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            log.info("init1" + this.getClass());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User user = createUser("user1", encoder.encode("0000"), "김철수", "010-1111-2222", "kim@gmail.com", "여행러", Role.ROLE_USER);
            em.persist(user);
            User user2 = createUser("user2", encoder.encode("0000"), "김영희", "010-7777-8888", "young@gmail.com", "여행좋아", Role.ROLE_NOTCERTIFIED);
            em.persist(user2);
//            User user3 = createUser("admin", encoder.encode("0000"), "관리자", "010-5656-5656", "admin@gmail.com", "관리자", Role.ROLE_ADMIN);
//            em.persist(user3);
//            User user4 = createUser("manager", encoder.encode("0000"), "호텔매니저", "010-8989-8989", "hotel@gmail.com", "호텔매니저", Role.ROLE_MANAGER);
//            em.persist(user4);

            CreateBoardDto createBoardDto = new CreateBoardDto();
            createBoardDto.setTitle("메이트 구함");
            createBoardDto.setDestination("제주도");
            createBoardDto.setRecruitment(1);
            createBoardDto.setContent("구해요");

            Board board = new Board(createBoardDto, user);
            em.persist(board);
        }

        private User createUser(String memId, String password, String name, String phone, String email, String nickname, Role auth) {
            User user = new User(memId, password, name, phone, email, nickname, auth);
            return user;
        }

    }
}
