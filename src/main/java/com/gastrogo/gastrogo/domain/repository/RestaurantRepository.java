package com.gastrogo.gastrogo.domain.repository;

import com.gastrogo.gastrogo.domain.entity.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
  Restaurant save(Restaurant restaurant);

  Optional<Restaurant> findById(String id);

  List<Restaurant> findAll();

  List<Restaurant> findByNameContaining(String name);

  List<Restaurant> findByLocation(String location);

  List<Restaurant> findByCuisineType(String cuisineType);
}
