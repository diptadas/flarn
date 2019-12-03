package edu.baylor.flarn.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The {@link UserControllerTests} contains the tests for the {@link UserController}.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@gm.com", roles = {"ADMIN"})
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("admin@gm.com")
                ))
                .andExpect(content().string(
                        containsString("moderator0@gm.com")
                ))
                .andExpect(content().string(
                        containsString("learner0@gm.com")
                ));
    }

    @Test
    public void testGetUsersByType() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("admin@gm.com")
                ));
    }

    @Test
    public void testSearchUsers() throws Exception {
        mockMvc.perform(get("/users?name=mock")) // substring of Admin Mock
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("Admin Mock")
                ));
    }
}
