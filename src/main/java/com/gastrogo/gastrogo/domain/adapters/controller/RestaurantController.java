package com.gastrogo.gastrogo.domain.adapters.controller;

import com.gastrogo.gastrogo.application.usecase.CreateRestaurantUseCase;
import com.gastrogo.gastrogo.application.usecase.SearchRestaurantsUseCase;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

  private final CreateRestaurantUseCase createRestaurantUseCase;
  private final SearchRestaurantsUseCase searchRestaurantsUseCase;

  /**
   * Cadastra um novo restaurante.
   * @param restaurant Objeto JSON do restaurante (nome, localização, capacidade etc.).
   * @return Restaurante criado com ID preenchido.
   */
  @PostMapping
  public ResponseEntity<Restaurant> create(@RequestBody Restaurant restaurant) {
    Restaurant saved = createRestaurantUseCase.execute(restaurant);
    return ResponseEntity.ok(saved);
  }

  /**
   * Busca restaurantes por ID (caso seja /{id}) ou faz buscas customizadas (por nome, localização, etc.).
   * Aqui são só exemplos; você pode criar endpoints separados ou query params mais específicos.
   */
  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> findById(@PathVariable String id) {
    return searchRestaurantsUseCase.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/search/by-name")
  public ResponseEntity<List<Restaurant>> findByName(@RequestParam String name) {
    return ResponseEntity.ok(searchRestaurantsUseCase.findByName(name));
  }

  @GetMapping("/search/by-location")
  public ResponseEntity<List<Restaurant>> findByLocation(@RequestParam String location) {
    return ResponseEntity.ok(searchRestaurantsUseCase.findByLocation(location));
  }

  @GetMapping("/search/by-cuisine")
  public ResponseEntity<List<Restaurant>> findByCuisine(@RequestParam String cuisine) {
    return ResponseEntity.ok(searchRestaurantsUseCase.findByCuisine(cuisine));
  }
}
