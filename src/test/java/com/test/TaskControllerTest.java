package com.test;

import com.project.Application;
import com.project.controller.TaskController;
import com.project.helpers.StatusEnum;
import com.project.model.Task;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest(classes = Application.class)
@TestPropertySource("/test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public TaskController taskController;


    @Test
    @Order(1)
    public void testMainPage() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Description")));
    }

    @Test
    @Order(2)
    public void testCreateRedirect() throws Exception {
        Task task = new Task();
        task.setId(Integer.MAX_VALUE-7);
        task.setDescription("springTestDescription");
        task.setStatus(StatusEnum.IN_PROGRESS);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/createTask")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("description", "spring test description")
                .param("status", String.valueOf(StatusEnum.IN_PROGRESS))
                .sessionAttr("task", task))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
    }
    @Test
    @Order(4)
    public void testDeleteRedirect() throws Exception {
        mockMvc.perform(post("/deleteTask/{id}", Integer.MAX_VALUE-10))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
    @Test
    @Order(3)
    public void testUpdateRedirect() throws Exception {
        Task task = new Task();
        task.setId(Integer.MAX_VALUE-7);
        task.setDescription("springTestDescription");
        task.setStatus(StatusEnum.IN_PROGRESS);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/updateTask")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("description", "spring test description update")
                        .param("status", String.valueOf(StatusEnum.DONE))
                        .sessionAttr("task", task))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

}
