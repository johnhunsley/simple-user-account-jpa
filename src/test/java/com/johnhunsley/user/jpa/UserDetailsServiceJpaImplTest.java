package com.johnhunsley.user.jpa;

import com.johnhunsley.user.jpa.domain.UserJpaImpl;
import com.johnhunsley.user.jpa.repository.UserRepositoryJpaImpl;
import com.johnhunsley.user.jpa.service.UserDetailsServiceJpaImpl;
import com.johnhunsley.user.service.UserDetailsServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

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
        final long totalItems = 10L;
        final int totalPages = 2;
        UserJpaImpl user = new UserJpaImpl();
        List<UserJpaImpl> users = new ArrayList<>();
        users.add(user);
        org.springframework.data.domain.Page<UserJpaImpl> mockPage = Mockito.mock(Page.class);
        when(mockPage.getTotalElements()).thenReturn(totalItems);
        when(mockPage.getTotalPages()).thenReturn(totalPages);
        when(mockPage.getContent()).thenReturn(users);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        com.johnhunsley.user.domain.Page page = userDetailsService.pageAllUser(5,1);
        assertTrue(page.getTotalItems() == totalItems);
        assertTrue(page.getTotalPages() == totalPages);
        assertFalse(page.getPagedItems().isEmpty());
    }

}
