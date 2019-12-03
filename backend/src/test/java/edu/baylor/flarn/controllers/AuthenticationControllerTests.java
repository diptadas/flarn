package edu.baylor.flarn.controllers;

import com.google.gson.Gson;
import edu.baylor.flarn.resources.AuthenticationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
@WithMockUser(username = "admin@gm.com", roles = {"ADMIN"})
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(Object obj) {
        return new Gson().toJson(obj);
    }

    @Test
    public void testRegisterInvalid() throws Exception {
        mockMvc.perform(
                post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content("") // invalid content
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void testLoginFailed() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername("admin@gm.com");
        authRequest.setPassword("wrongPass");

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(authRequest))
        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void testLoginPassed() throws Exception {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername("admin@gm.com");
        authRequest.setPassword("admin");

        mockMvc.perform(
                post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(authRequest))
        ).andExpect(status().isOk());
    }
}
