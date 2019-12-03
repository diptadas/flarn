package edu.baylor.flarn.controllers;

import edu.baylor.flarn.models.Category;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static edu.baylor.flarn.controllers.AuthenticationControllerTest.asJsonString;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The {@link CategoryControllerTest} contains the tests for the {@link CategoryController}.
 *
 * @author Dipta Das
 * @author Clinton Yeboah
 * @author Frimpong Boadu
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "admin@gm.com", roles = {"ADMIN"})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetCategories() throws Exception {
        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("category-0")
                ))
                .andExpect(content().string(
                        containsString("category-1")
                ))
                .andExpect(content().string(
                        containsString("category-2")
                ));
    }

    @Test
    public void testCreateCategory() throws Exception {
        Category category = new Category();
        category.setName("new-category");

        mockMvc.perform(
                post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(asJsonString(category)))
                .andExpect(status().isOk())
                .andExpect(content().string(
                        containsString("new-category")
                ));
    }
}
