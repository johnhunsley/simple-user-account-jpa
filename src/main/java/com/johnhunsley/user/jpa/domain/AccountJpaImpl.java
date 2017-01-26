package com.johnhunsley.user.jpa.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 *  __________        _________        __________        _________
 * |          |      |         |      |          |      |         |
 * | Account  |-----<|  User   |-----<| UserRole |>-----|  Role   |
 * |__________|      |_________|      |__________|      |_________|
 *
 *  An Account to which {@link User} instances are associated
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
 *
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 19:09
 */
@Entity
@Table(name = "ACCOUNT", catalog = "simpleuseraccount", schema = "")
public class AccountJpaImpl implements Account, Serializable {
    private static final long serialVersionUID = 333L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    @Column(name = "NAME")
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserJpaImpl> users;

    @Override
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<UserJpaImpl> getUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        if(users == null) users = new HashSet<>();
        users.add((UserJpaImpl)user);
    }
}
