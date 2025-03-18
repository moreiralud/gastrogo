package com.gastrogo.gastrogo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantIntegrationTest {

  @Container
  static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0")
          .withExposedPorts(27017);

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testCreateAndGetRestaurant() throws Exception {
    // Cria um objeto Restaurant para teste
    Restaurant newRestaurant = new Restaurant("Integration Restaurant", "Downtown", "Italian", 50, "8h-22h");
    String restaurantJson = objectMapper.writeValueAsString(newRestaurant);

    // Envia uma requisição POST para criar o restaurante com httpBasic e CSRF (ajuste se necessário)
    MvcResult postResult = mockMvc.perform(post("/api/restaurants")
                    .with(httpBasic("user", "password"))
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(restaurantJson))
            .andExpect(status().isOk())
            .andReturn();

    // Converte a resposta JSON em um objeto Restaurant
    String postResponse = postResult.getResponse().getContentAsString();
    Restaurant createdRestaurant = objectMapper.readValue(postResponse, Restaurant.class);

    // Verifica se o restaurante foi criado e possui um ID gerado
    assertNotNull(createdRestaurant.getId(), "O ID não deve ser nulo");
    assertEquals("Integration Restaurant", createdRestaurant.getName());

    // Realiza uma requisição GET para buscar o restaurante criado pelo ID
    mockMvc.perform(get("/api/restaurants/" + createdRestaurant.getId())
                    .with(httpBasic("user", "password"))
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Integration Restaurant"))
            .andExpect(jsonPath("$.location").value("Downtown"));
  }
}
