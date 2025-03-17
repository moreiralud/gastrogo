package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.CreateRestaurantUseCase;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRestaurantService implements CreateRestaurantUseCase {

  private final RestaurantRepository restaurantRepository;

  @Override
  public Restaurant execute(Restaurant restaurant) {
    // Validações básicas
    if (restaurant.getName() == null || restaurant.getName().isBlank()) {
      throw new IllegalArgumentException("Restaurant name is required.");
    }
    if (restaurant.getLocation() == null || restaurant.getLocation().isBlank()) {
      throw new IllegalArgumentException("Location is required.");
    }
    // ... etc.

    return restaurantRepository.save(restaurant);
  }
}
