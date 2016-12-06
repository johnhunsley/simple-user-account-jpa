package com.johnhunsley.user.jpa;

import com.johnhunsley.user.jpa.repository.UserRepositoryJpaImpl;
import com.johnhunsley.user.jpa.service.UserDetailsServiceJpaImpl;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 05/12/2016
 *         Time : 16:13
 */
@RunWith(MockitoJUnitRunner.class)
public class UserDetailsServiceJpaImplTest {

    @Mock
    UserRepositoryJpaImpl userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService = new UserDetailsServiceJpaImpl();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userDetailsService.setUserRepository(userRepository);
    }

    @Test
    public void testPageAllUser() {
        assertTrue(true); //todo write this
    }

}
