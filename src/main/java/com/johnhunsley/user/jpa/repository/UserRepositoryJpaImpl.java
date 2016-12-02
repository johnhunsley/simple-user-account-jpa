package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 12:52
 */
@Repository("userRepository")
@Profile("jpa")
public interface UserRepositoryJpaImpl extends UserRepository, JpaRepository<UserJpaImpl, Long> {

    @Override
    UserJpaImpl findByUsername(String username);

    @Override
    UserJpaImpl findById(Long id);

}
