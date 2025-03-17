package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.SearchRestaurantsUseCase;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchRestaurantsService implements SearchRestaurantsUseCase {

  private final RestaurantRepository restaurantRepository;

  @Override
  public List<Restaurant> findByName(String name) {
    return restaurantRepository.findByNameContaining(name);
  }

  @Override
  public List<Restaurant> findByLocation(String location) {
    return restaurantRepository.findByLocation(location);
  }

  @Override
  public List<Restaurant> findByCuisine(String cuisineType) {
    return restaurantRepository.findByCuisineType(cuisineType);
  }

  @Override
  public Optional<Restaurant> findById(String id) {
    return restaurantRepository.findById(id);
  }

}
