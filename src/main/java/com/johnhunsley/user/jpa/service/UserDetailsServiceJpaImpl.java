package com.johnhunsley.user.jpa.service;

import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.jpa.repository.UserRepositoryJpaImpl;
import com.johnhunsley.user.jpa.repository.UserSpecification;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 05/12/2016
 *         Time : 10:12
 */
@Service("userDetailsService")
@Profile("jpa")
public class UserDetailsServiceJpaImpl extends UserDetailsServiceImpl<UserRepositoryJpaImpl> {

    @Override
    public Collection<UserJpaImpl> pageAllUser(final int pageSize, final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "username");
        return userRepository.findAll(request).getContent();
    }

    @Override
    public Collection<UserJpaImpl> pageAccountUsers(Account account, final int pageSize, final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "username");
        return userRepository.findByAccount(account, request).getContent();
    }

    @Override
    public Collection<UserJpaImpl> searchAllUsers(final String query, final int pageSize, final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "username");
        return userRepository.findAll(new UserSpecification(query), request).getContent();
    }

    @Override
    public Collection<UserJpaImpl> searchAccountUsers(Account account,
                                                      final String query,
                                                      final int pageSize,
                                                      final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "username");
        return userRepository.findAll(new UserSpecification(query) {

            @Override
            public Predicate toPredicate(Root<UserJpaImpl> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.and(builder.equal(root.get("account"), account),
                                   super.toPredicate(root, query, builder) );
            }

        }, request).getContent();
    }
}