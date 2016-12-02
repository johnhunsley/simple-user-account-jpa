package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.jpa.domain.AccountJpaImpl;
import com.johnhunsley.user.repository.AccountRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/12/2016
 *         Time : 11:21
 */
@Repository("accountRepository")
@Profile("jpa")
public interface AccountRepositoryJpaImpl extends AccountRepository, CrudRepository<AccountJpaImpl, Integer> {

    @Override
    Account findById(Account account);
}
