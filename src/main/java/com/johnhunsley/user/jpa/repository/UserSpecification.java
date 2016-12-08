package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * <p>
 *     Implementation of the {@link Specification} stereotype predicating {@link UserJpaImpl} instances by
 *     username OR first name OR last name for a given search value where that value matches anywhere
 *     within the values of those fields.
 * </p>
 *
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
 *         Time : 19:08
 */
public class UserSpecification implements Specification<UserJpaImpl> {

    private final String value;

    public UserSpecification(final String value) {
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<UserJpaImpl> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.or(
                builder.like(root.get("username"),  "%"+value+"%"),
                builder.like(root.get("firstName"), "%"+value+"%"),
                builder.like(root.get("lastName"),  "%"+value+"%")
        );
    }
}
