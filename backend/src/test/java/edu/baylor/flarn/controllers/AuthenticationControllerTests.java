package edu.baylor.flarn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.baylor.flarn.resources.AuthenticationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The {@link AuthenticationControllerTests} contains the tests for the {@link AuthenticationController}.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLoginFailed() throws Exception {

        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername("admin@gm.com");
        authRequest.setPassword("wrongPass");

        mockMvc.perform(post("auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(authRequest)))
                .andExpect(status().isForbidden());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
