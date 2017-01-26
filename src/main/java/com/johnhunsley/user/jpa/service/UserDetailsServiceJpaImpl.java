package com.johnhunsley.user.jpa.service;

import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.domain.Page;
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
 * <p>
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 * </p>
 *
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 05/12/2016
 *         Time : 20:12
 */
@Service("userDetailsService")
@Profile("jpa")
public class UserDetailsServiceJpaImpl extends UserDetailsServiceImpl<UserRepositoryJpaImpl> {

    @Override
    public Page<UserJpaImpl> pageAllUser(final int pageSize, final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        org.springframework.data.domain.Page<UserJpaImpl> jpaPage =  userRepository.findAll(request);
        return new Page(jpaPage.getContent(), jpaPage.getTotalElements(), jpaPage.getTotalPages());
    }

    @Override
    public Collection<UserJpaImpl> pageAccountUsers(Integer accountId, final int pageSize, final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        return userRepository.findByAccountId(accountId, request).getContent();
    }

    @Override
    public Page<UserJpaImpl> searchAllUsers(final String query, final int pageSize, final int pageNumber) {

        if(query == null || query.length() < 1) {
            return pageAllUser(pageSize, pageNumber);

        } else {
            PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
            org.springframework.data.domain.Page<UserJpaImpl> jpaPage = userRepository.searchUsersByName(query, request);
            return new Page(jpaPage.getContent(), jpaPage.getTotalElements(), jpaPage.getTotalPages());
        }

    }

    /**
     * <p>
     *     Add the 'account' instance as a query to the builder AND the query value as specified
     *     in the {@link UserSpecification}
     * </p>
     * @param account
     * @param query
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public Collection<UserJpaImpl> searchAccountUsers(Account account,
                                                      final String query,
                                                      final int pageSize,
                                                      final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        return userRepository.findAll(new UserSpecification(query) {

            @Override
            public Predicate toPredicate(Root<UserJpaImpl> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.and(builder.equal(root.get("account"), account),
                                   super.toPredicate(root, query, builder) );
            }

        }, request).getContent();
    }
}