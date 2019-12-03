package edu.baylor.flarn.controllers;

import edu.baylor.flarn.resources.ProblemSearchRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static edu.baylor.flarn.controllers.AuthenticationControllerTests.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The {@link ProblemControllerTests} contains the tests for the {@link ProblemController}.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@gm.com", roles = {"ADMIN"})
public class ProblemControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProblems() throws Exception {
        mockMvc.perform(get("/problems"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("problem-0")
                ))
                .andExpect(content().string(
                        containsString("problem-1")
                ))
                .andExpect(content().string(
                        containsString("problem-2")
                ));
    }

    @Test
    public void testSearchProblem() throws Exception {
        ProblemSearchRequest request = new ProblemSearchRequest();
        request.setTitle("lem-2"); // substring of problem-02

        mockMvc.perform(
                post("/problems/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("problem-2")
                ));
    }
}
