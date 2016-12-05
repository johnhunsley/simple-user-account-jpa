package com.johnhunsley.user.jpa.domain;

import com.johnhunsley.user.domain.Role;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 14:43
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
