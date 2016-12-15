package com.johnhunsley.user.jpa;

import com.johnhunsley.user.domain.*;
import com.johnhunsley.user.jpa.domain.AccountJpaImpl;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.jpa.repository.AccountRepositoryJpaImpl;
import com.johnhunsley.user.jpa.repository.RoleRepositoryJpaImpl;
import com.johnhunsley.user.jpa.repository.UserRepositoryJpaImpl;
import com.johnhunsley.user.jpa.repository.UserSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

import static org.junit.Assert.*;

/**
 * <p>
 *     Spring Boot will automatically initialize HSQLDB as the hsqldb jar is on the class path.
 *     This database will exist only at runtime for the test as that dependency is test scoped.
 *
 *     see the following dependency in the pom -
 *
 *      <dependency>
 *          <groupId>org.hsqldb</groupId>
 *          <artifactId>hsqldb</artifactId>
 *          <scope>test</scope>
 *          <optional>true</optional>
 *      </dependency>
 *
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/12/2016
 *         Time : 09:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@EnableJpaRepositories(basePackages = "com.johnhunsley.user.jpa")
@EnableTransactionManagement
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"jpa", "integration"})
public class RepositoryJpaIntegrationTest {
    final String username = "johnhunsley";
    final String roleAuthority = "ROLE_TEST";
    Account account;
    Long userId;

    @Autowired
    private UserRepositoryJpaImpl userRepository;

    @Autowired
    private AccountRepositoryJpaImpl accountRepository;

    @Autowired
    private RoleRepositoryJpaImpl roleRepository;

    /**
     * Persist some objects into HSQLDB
     */
    @Before
    public void initData() {
        cleanup();
        Hash hash = new Hash(Hash.SHA1_TYPE);
        Role role = new RoleJpaImpl(roleAuthority);
        roleRepository.save(role);

        account = new AccountJpaImpl();
        accountRepository.save(account);

        User user = new UserJpaImpl(username, "jphunsley@gmail.com", "test", "test", YNEnum.Y, hash.hash("password"));
        user.setAccount(account);
        user.addRole(role);
        userRepository.save(user);
        userId = user.getId();
        assertNotNull(userId);
    }

    /**
     * Delete the lot
     */
    @After
    public void cleanup() {
        userRepository.delete(userRepository.findAll());
        accountRepository.delete(accountRepository.findAll());
        roleRepository.delete(roleRepository.findAll());
    }

    @Test
    public void testFindByUsername() {
        User persistent = userRepository.findByUsername(username);
        assertNotNull(persistent);
        assertTrue(persistent.getId().equals(userId));
        System.out.println(persistent.getId());
        assertNotNull(persistent.getAccount());
        assertTrue(persistent.getAccount().getId() > 0);
        assertNotNull(persistent.getAuthorities());
        assertFalse(persistent.getAuthorities().isEmpty());
        assertTrue(persistent.getAuthorities().size() == 1);
        assertTrue(persistent.getAuthorities().iterator().next().getAuthority().equals(roleAuthority));
    }

    @Test
    public void testFindById() {
        User persistent = userRepository.findById(userId);
        assertNotNull(persistent);
        assertTrue(persistent.getId().equals(userId));
        System.out.println(persistent.getId());
        assertNotNull(persistent.getAccount());
        assertTrue(persistent.getAccount().getId() > 0);
        assertNotNull(persistent.getAuthorities());
        assertFalse(persistent.getAuthorities().isEmpty());
        assertTrue(persistent.getAuthorities().size() == 1);
        assertTrue(persistent.getAuthorities().iterator().next().getAuthority().equals(roleAuthority));
    }

    @Test
    public void testFindByAccount() {
        Page<UserJpaImpl> page = userRepository.findByAccount(
                                            account, new PageRequest(0, 10, Sort.Direction.DESC, "username"));
        assertFalse(page.getContent().isEmpty());
        assertTrue(page.getContent().get(0).getId().equals(userId));
    }

    /**
     * <p>
     *     Add another User to the store with a different name so we can assert it
     *     doesn't get returned in the search results
     * </p>
     */
    @Test
    public void testQueryAllByValue() {
        User user = new UserJpaImpl("bob", "bob@bob", "bob", "bob", YNEnum.Y, "unhashed".getBytes());
        user.setAccount(account);
        userRepository.save(user);
        List<UserJpaImpl> results = userRepository.findAll(new UserSpecification("tes"));
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.size() == 1);
        assertTrue(results.get(0).getId().equals(userId));
    }
}
