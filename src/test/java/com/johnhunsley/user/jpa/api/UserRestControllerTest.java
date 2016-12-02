package com.johnhunsley.user.jpa.api;

import com.johnhunsley.user.Application;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author John Hunsley
 *         jphunsley@gmail.com
 *         Date : 02/12/2016
 *         Time : 14:13
 */
@JsonTest
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = {"jpa", "integration"})
public class UserRestControllerTest {

    @Autowired
    private UserRestController userRestController;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountRepository accountRepository;

    private MockMvc mockMvc;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
    }

    @Test
    public void testGetUser() throws Exception {
        final String username = "controllertest";
        final String roleAuthority = "ROLE_READ_TEST";

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
        System.out.println(persistent.toString());

        mockMvc.perform(get("/user/username/"+persistent.getUsername())).andExpect(status().isOk());
        mockMvc.perform(get("/user/id/"+persistent.getId())).andExpect(status().isOk());
    }

    @Test
    public void testWriteUser() throws Exception {
        final String json = "{\"id\":null,\"username\":\"test\",\"password\":\"UxViU7towYwsi5G3zZlzNS3Gkbg=\",\"email\":\"test@tesst\",\"firstName\":\"test\",\"lastName\":\"test\",\"active\":true,\"enabled\":true,\"accountNonExpired\":true,\"accountNonLocked\":true,\"credentialsNonExpired\":true}";
        mockMvc.perform(put("/user").content(json).contentType("application/json")).andExpect(status().isOk());

        User result = userRepository.findByUsername("usernamewrite");
        assertNotNull(result);
        assertTrue(result.getId() > 0);

    }


}
