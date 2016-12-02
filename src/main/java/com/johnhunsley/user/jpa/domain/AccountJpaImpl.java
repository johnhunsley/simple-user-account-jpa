package com.johnhunsley.user.jpa.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 15:09
 */
@Entity
@Table(name = "ACCOUNTS")
public class AccountJpaImpl implements Account, Serializable {
    private static final long serialVersionUID = 333L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonManagedReference
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserJpaImpl> users;

    @Override
    public Integer getId() {
        return id;
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
