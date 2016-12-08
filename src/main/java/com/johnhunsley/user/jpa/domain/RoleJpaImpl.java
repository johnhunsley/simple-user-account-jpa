package com.johnhunsley.user.jpa.domain;

import com.johnhunsley.user.domain.Role;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 *  __________        _________        __________        _________
 * |          |      |         |      |          |      |         |
 * | Account  |-----<|  User   |-----<| UserRole |>-----|  Role   |
 * |__________|      |_________|      |__________|      |_________|
 *
 *  A Role defines a Spring Security {@link org.springframework.security.core.GrantedAuthority} within the system
 *  and can be granted to {@link com.johnhunsley.user.domain.User} instances
 * </p>
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
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 19:43
 */
@Entity
@Table(name = "ROLE", catalog = "simple-user-account", schema = "")
public class RoleJpaImpl implements Role, Serializable {
    private static final long serialVersionUID = 444L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    @Column(name = "NAME")
    private String authority;

    public RoleJpaImpl() {}

    /**
     *
     * @param authority
     */
    public RoleJpaImpl(String authority) {
        this.authority = authority;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    @Override
    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
