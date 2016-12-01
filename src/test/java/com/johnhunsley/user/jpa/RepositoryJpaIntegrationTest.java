package com.johnhunsley.user.jpa;

import com.johnhunsley.user.domain.Account;
import com.johnhunsley.user.domain.User;
import com.johnhunsley.user.domain.YNEnum;
import com.johnhunsley.user.repository.AccountRepository;
import com.johnhunsley.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 01/12/2016
 *         Time : 09:35
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@ComponentScan
@EnableAutoConfiguration
@ActiveProfiles("jpa")
public class RepositoryJpaIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void testReadWrite() {
        final String username = "test";
        final String roleAuthority = "ROLE_TEST";

        Account account = new AccountJpaImpl();
        accountRepository.save(account);
        User user = new UserJpaImpl(username, "test@tesst", "test", "test", YNEnum.Y, "unhashed".getBytes());
        user.setAccount(account);
        userRepository.save(user);
        User persistent = userRepository.findByUsername(username);
        assertNotNull(persistent);
        assertTrue(persistent.getId() > 0);
    }
}
