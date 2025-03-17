package com.gastrogo.gastrogo.application.usecase;

import com.gastrogo.gastrogo.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface SearchRestaurantsUseCase {
  List<Restaurant> findByName(String name);
  List<Restaurant> findByLocation(String location);
  List<Restaurant> findByCuisine(String cuisineType);
  Optional<Restaurant> findById(String id);

}
