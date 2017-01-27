package com.johnhunsley.user.jpa.service;

import com.johnhunsley.user.domain.Page;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.jpa.repository.RoleRepositoryJpaImpl;
import com.johnhunsley.user.service.RoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 *  <p>
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
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 27/01/2017
 *         Time : 13:36
 */
@Service("roleService")
@Profile("jpa")
public class RoleServiceJpaImpl extends RoleService<RoleRepositoryJpaImpl> {

    @Override
    public Page<RoleJpaImpl> pageAllRoles(final int pageSize, final int pageNumber) {
        PageRequest request = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.ASC, "id");
        org.springframework.data.domain.Page<RoleJpaImpl> jpaPage = roleRepository.findAll(request);
        return new Page<>(jpaPage.getContent(), jpaPage.getTotalElements(), jpaPage.getTotalPages());
    }
}
