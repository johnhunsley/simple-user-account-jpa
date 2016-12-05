package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 05/12/2016
 *         Time : 14:08
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
