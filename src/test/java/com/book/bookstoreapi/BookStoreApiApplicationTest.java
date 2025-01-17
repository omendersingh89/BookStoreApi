
package com.book.bookstoreapi;

import com.book.bookstoreapi.model.Book;
import com.book.bookstoreapi.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookStoreApiApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddBook() throws Exception {
        String bookJson = "{\"title\": \"Test Book\", \"author\": \"Test Author\", \"price\": 100.0}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    void testGetAllBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetBookById() throws Exception {
        String bookJson = "{\"title\": \"Test Book\", \"author\": \"Test Author\", \"price\": 100.0}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    void testUpdateBook() throws Exception {
        String bookJson = "{\"title\": \"Test Book\", \"author\": \"Test Author\", \"price\": 100.0}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk());

        String updatedBookJson = "{\"title\": \"Updated Book\", \"author\": \"Updated Author\", \"price\": 150.0}";

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedBookJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"))
                .andExpect(jsonPath("$.author").value("Updated Author"))
                .andExpect(jsonPath("$.price").value(150.0));
    }

    @Test
    void testDeleteBook() throws Exception {
        String bookJson = "{\"title\": \"Test Book\", \"author\": \"Test Author\", \"price\": 100.0}";

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound());
    }
}