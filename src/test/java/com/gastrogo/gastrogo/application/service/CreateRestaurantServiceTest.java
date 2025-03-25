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
    restaurantRepository = Mockito.mock(RestaurantRepository.class);
    createRestaurantUseCase = new CreateRestaurantService(restaurantRepository);
  }

  @Test
  void shouldCreateRestaurantSuccessfully() {
    // Arrange
    Restaurant input = new Restaurant("Teste", "Local A", "Italiana", 50, "8h-22h");

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
      assert false : "Deveria ter lançado IllegalArgumentException";
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("name is required");
    }
  }

}
