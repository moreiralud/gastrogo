package com.gastrogo.gastrogo.application.usecase;


import com.gastrogo.gastrogo.domain.entity.Restaurant;

public interface CreateRestaurantUseCase {
  Restaurant execute(Restaurant restaurant);
}
