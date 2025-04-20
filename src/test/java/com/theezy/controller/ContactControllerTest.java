package com.theezy.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

        @Test
        public void testAddContactEndpoint() throws Exception {
            String userId = "67fedbf9e968d55b0e9532f9";

            String contactJson = """
                {
                    "name": "Theezy",
                    "email": "Theezy@gmail.com",
                    "phoneNumber": "09036111444",
                    "address": "Sabo, yaba."
                }
                """;

            mockMvc.perform(post("/contacts/addcontact/user/" + userId + "/contacts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(contactJson))
                    .andExpect((status().isCreated()))
                    .andDo(print());
        }
}