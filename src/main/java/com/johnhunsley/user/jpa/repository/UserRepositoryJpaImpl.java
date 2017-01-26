package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
 *         Date : 30/11/2016
 *         Time : 19:52
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

    Page<UserJpaImpl> findByAccountId(Integer accountId, Pageable pageable);

    @Query("select u from UserJpaImpl u where u.username like concat('%',:queryValue,'%') or u.firstName like concat('%',:queryValue,'%') or u.lastName like concat('%',:queryValue,'%')")
    Page<UserJpaImpl> searchUsersByName(@Param("queryValue")String queryValue, Pageable pageable);

//    Page<UserJpaImpl> searchAccountUsersByName(String queryValue, Account acocunt, Pageable pageable);

}
