package com.gastrogo.gastrogo.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestaurantSteps {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private Restaurant restaurant;
  private MvcResult mvcResult;

  @Before
  public void setup() {
    // Se necessário, configure dados iniciais ou mocks.
  }

  @Given("um restaurante com nome {string}, localização {string}, tipo {string}, capacidade {int} e horário de funcionamento {string}")
  public void um_restaurante_com_detalhes(String name, String location, String type, int capacity, String openingHours) {
    restaurant = new Restaurant(name, location, type, capacity, openingHours);
  }

  @When("eu crio o restaurante")
  public void eu_crio_o_restaurante() throws Exception {
    String json = objectMapper.writeValueAsString(restaurant);
    mvcResult = mockMvc.perform(post("/api/restaurants")
                    .contentType("application/json")
                    .content(json))
            .andExpect(status().isOk())
            .andReturn();
  }

  @Then("o restaurante é criado com um ID gerado")
  public void o_restaurante_e_criado_com_um_ID_gerado() throws Exception {
    String response = mvcResult.getResponse().getContentAsString();
    Restaurant created = objectMapper.readValue(response, Restaurant.class);
    assertNotNull(created.getId(), "O ID não deve ser nulo");
    restaurant = created; // Atualiza o objeto para uso posterior
  }

  @Then("eu posso recuperar o restaurante com nome {string} e localização {string}")
  public void eu_posso_recuperar_o_restaurante_com_detalhes(String expectedName, String expectedLocation) throws Exception {
    mvcResult = mockMvc.perform(get("/api/restaurants/" + restaurant.getId())
                    .contentType("application/json"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(expectedName))
            .andExpect(jsonPath("$.location").value(expectedLocation))
            .andReturn();
  }
}
