package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 12:52
 */
@Repository("userRepository")
@Profile("jpa")
public interface UserRepositoryJpaImpl extends UserRepository,
                                               JpaRepository<UserJpaImpl, Long>,
                                               JpaSpecificationExecutor<UserJpaImpl> {

    @Override
    UserJpaImpl findByUsername(String username);

    @Override
    UserJpaImpl findById(Long id);


    Page<UserJpaImpl> findByAccount(Account account, Pageable pageable);

}
