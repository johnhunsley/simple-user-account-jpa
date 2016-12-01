package com.johnhunsley.user.jpa;

import com.johnhunsley.user.domain.Role;

import javax.persistence.*;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 30/11/2016
 *         Time : 14:43
 */
@Entity
@Table(name = "ROLE")
public class RoleJpaImpl implements Role {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer Id;

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
        return Id;
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
