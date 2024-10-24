package com.github.brice.todolistapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.brice.todolistapi.adapter.in.rest.resource.TokenResponse;
import com.github.brice.todolistapi.adapter.in.rest.resource.response.TaskResponse;
import com.github.brice.todolistapi.adapter.out.persistence.TokenJpaRepository;
import com.github.brice.todolistapi.adapter.out.persistence.UserJpaRepository;
import com.github.brice.todolistapi.adapter.out.persistence.task.TaskJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoListApiApplicationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private TaskJpaRepository taskJpaRepository;
    @Autowired
    private TokenJpaRepository tokenJpaRepository;
    private String token;
    private Long createTaskId;

    @BeforeEach
    void setup() throws Exception {
        registerAnAccount();
        token = login();
        createTaskId = createTask(token);
    }

    private Long createTask(String token) throws Exception {
        var taskResponse = mockMvc.perform(post("/todos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "title": "Title",
                                    "description": "Description"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse();
        return objectMapper.readValue(taskResponse.getContentAsString(), TaskResponse.class).id();
    }

    private String login() throws Exception {
        var token = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "email": "email@gmail.com",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse();
        return objectMapper.readValue(token.getContentAsByteArray(), TokenResponse.class).token();
    }

    private void registerAnAccount() throws Exception {
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "name": "Name",
                                    "email": "email@gmail.com",
                                    "password": "password"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @AfterEach
    void tearDown() {
        tokenJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
        taskJpaRepository.deleteAll();
    }

    @Test
    void updateExistingTask() throws Exception {
        updateTask(token);
    }

    private void updateTask(String token) throws Exception {
        mockMvc.perform(put("/todos/{id}", createTaskId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                                {
                                    "title": "New Title",
                                    "description": "New Description"
                                }
                                """))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Title"))
                .andExpect(jsonPath("$.description").value("New Description"));
    }

    @Test
    void processGettingAllTasks() throws Exception {
        getAllTasks();
    }

    private void getAllTasks() throws Exception {
        mockMvc.perform(get("/todos")
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void processDeleteTask() throws Exception {
        deleteTask(token);
    }

    private void deleteTask(String token) throws Exception {
        mockMvc.perform(delete("/todos/{id}", createTaskId)
                        .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}