package com.tripper.repository;

import com.tripper.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

/**
 * 회원 관련 레포지토리
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(String memId);

    @Query("SELECT u FROM User u WHERE u.memId = :memId")
    User findByMemId(@Param("memId") String memId);

    @Query("SELECT u.emailAuthCode FROM User u WHERE u.memId = :memId")
    String getEmailAuthCode(@Param("memId") String memId);

}
