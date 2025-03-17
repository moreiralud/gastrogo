package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.CreateRestaurantUseCase;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateRestaurantServiceTest {

  private RestaurantRepository restaurantRepository;
  private CreateRestaurantUseCase createRestaurantUseCase;

  @BeforeEach
  void setUp() {
    // Cria um mock do repositório
    restaurantRepository = Mockito.mock(RestaurantRepository.class);
    // Instancia o caso de uso, injetando o repositório mockado
    createRestaurantUseCase = new CreateRestaurantService(restaurantRepository);
  }

  @Test
  void shouldCreateRestaurantSuccessfully() {
    // Arrange
    Restaurant input = new Restaurant("Teste", "Local A", "Italiana", 50, "8h-22h");

    // Simula que ao salvar, retorna o mesmo objeto (ou setaria um ID fictício, etc.)
    when(restaurantRepository.save(any(Restaurant.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    Restaurant result = createRestaurantUseCase.execute(input);

    // Assert
    verify(restaurantRepository, times(1)).save(input);
    assert result != null;
    assert "Teste".equals(result.getName());
  }

  @Test
  void shouldThrowExceptionWhenNameIsBlank() {
    // Arrange
    Restaurant input = new Restaurant("", "Local B", "Chinesa", 30, "10h-22h");

    // Act & Assert
    try {
      createRestaurantUseCase.execute(input);
      // Se chegar aqui, falhou, pois deveria lançar exceção
      assert false : "Deveria ter lançado IllegalArgumentException";
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("name is required");
    }
  }

  @Test
  void shouldThrowExceptionWhenLocationIsNull() {
    // Arrange
    Restaurant input = new Restaurant("Pizzaria", null, "Italiana", 30, "10h-22h");

    // Act & Assert
    try {
      createRestaurantUseCase.execute(input);
      assert false : "Deveria ter lançado IllegalArgumentException";
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("location is required");
    }
  }
}
