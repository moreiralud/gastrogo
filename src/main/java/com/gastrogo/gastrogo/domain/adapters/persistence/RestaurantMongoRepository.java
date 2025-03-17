package com.gastrogo.gastrogo.domain.adapters.persistence;

import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantMongoRepository extends
        MongoRepository<Restaurant, String>,
        RestaurantRepository
{
  // Vários métodos do MongoRepository já se alinham com a interface do domínio

  // Precisamos mapear os métodos custom
  // ex.: findByNameContaining
  List<Restaurant> findByNameContaining(String name);

  List<Restaurant> findByLocation(String location);

  List<Restaurant> findByCuisineType(String cuisineType);
}
