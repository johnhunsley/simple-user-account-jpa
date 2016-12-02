package com.johnhunsley.user.jpa.repository;

import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.domain.Role;
import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.domain.YNEnum;
import com.johnhunsley.user.jpa.domain.AccountJpaImpl;
import com.johnhunsley.user.jpa.domain.RoleJpaImpl;
import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.repository.AccountRepository;
import com.johnhunsley.user.repository.RoleRepository;
import com.johnhunsley.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 *     Spring Boot will automatically initialize HSQLDB as the hsqldb jar is on the class path.
 *     This database will exist only at runtime for the test as that dependency is test scoped.
 * </p>
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/12/2016
 *         Time : 09:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ActiveProfiles(profiles = {"jpa", "integration"})
public class RepositoryJpaIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testReadWrite() {
        final String username = "test";
        final String roleAuthority = "ROLE_TEST";

        Role role = new RoleJpaImpl(roleAuthority);
        roleRepository.save(role);

        Account account = new AccountJpaImpl();
        accountRepository.save(account);

        User user = new UserJpaImpl(username, "test@tesst", "test", "test", YNEnum.Y, "unhashed".getBytes());
        user.setAccount(account);
        user.addRole(role);
        userRepository.save(user);

        User persistent = userRepository.findByUsername(username);
        assertNotNull(persistent);
        assertTrue(persistent.getId() > 0);
        System.out.println(persistent.getId());
        assertNotNull(persistent.getAccount());
        assertTrue(persistent.getAccount().getId() > 0);
        assertNotNull(persistent.getAuthorities());
        assertFalse(persistent.getAuthorities().isEmpty());
        assertTrue(persistent.getAuthorities().size() == 1);
        assertTrue(persistent.getAuthorities().iterator().next().getAuthority().equals(roleAuthority));


    }
}
