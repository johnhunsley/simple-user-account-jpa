package com.johnhunsley.user.jpa;

import com.johnhunsley.user.domain.Role;
import com.johnhunsley.user.repository.RoleRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/12/2016
 *         Time : 13:42
 */
@Repository("userRepository")
@Profile("jpa")
public interface RoleRepositoryJpaImpl extends RoleRepository {

    @Override
    Collection<? extends Role> findAll();

    @Override
    void save(Role role);

    @Override
    Role findById(Integer integer);
}
