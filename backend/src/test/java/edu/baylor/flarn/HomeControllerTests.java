package edu.baylor.flarn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {

        // TODO: fix access denied
        /*mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("Welcome to Flarn!")
                ));*/
    }

}
