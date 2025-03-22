package com.gastrogo.gastrogo.domain.adapters.controller;

import com.gastrogo.gastrogo.application.usecase.CreateRestaurantUseCase;
import com.gastrogo.gastrogo.application.usecase.SearchRestaurantsUseCase;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
   */
  @Operation(
          summary = "Cadastra um novo restaurante",
          description = "Cria um restaurante com as informações fornecidas e retorna o restaurante criado com seu ID."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Restaurante criado com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
  })
  @PostMapping
  public ResponseEntity<Restaurant> create(
          @RequestBody
          @Parameter(
                  description = "Objeto JSON contendo os dados do restaurante",
                  required = true,
                  schema = @Schema(implementation = Restaurant.class)
          )
          Restaurant restaurant) {
    Restaurant saved = createRestaurantUseCase.execute(restaurant);
    return ResponseEntity.ok(saved);
  }

  /**
   * Busca um restaurante por ID.
   */
  @Operation(
          summary = "Busca restaurante por ID",
          description = "Retorna um restaurante com base no ID fornecido. Retorna 404 se não encontrado."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Restaurante encontrado"),
          @ApiResponse(responseCode = "404", description = "Restaurante não encontrado")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Restaurant> findById(
          @PathVariable
          @Parameter(description = "ID do restaurante", required = true)
          String id) {
    return searchRestaurantsUseCase.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
  }

  /**
   * Busca restaurantes por nome.
   */
  @Operation(
          summary = "Busca restaurantes por nome",
          description = "Retorna uma lista de restaurantes que contêm o nome informado."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Restaurantes encontrados")
  })
  @GetMapping("/search/by-name")
  public ResponseEntity<List<Restaurant>> findByName(
          @RequestParam
          @Parameter(description = "Nome para busca", required = true)
          String name) {
    return ResponseEntity.ok(searchRestaurantsUseCase.findByName(name));
  }

  /**
   * Busca restaurantes por localização.
   */
  @Operation(
          summary = "Busca restaurantes por localização",
          description = "Retorna uma lista de restaurantes localizados na área especificada."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Restaurantes encontrados")
  })
  @GetMapping("/search/by-location")
  public ResponseEntity<List<Restaurant>> findByLocation(
          @RequestParam
          @Parameter(description = "Localização para busca", required = true)
          String location) {
    return ResponseEntity.ok(searchRestaurantsUseCase.findByLocation(location));
  }

  /**
   * Busca restaurantes por tipo de cozinha.
   */
  @Operation(
          summary = "Busca restaurantes por tipo de cozinha",
          description = "Retorna uma lista de restaurantes que servem o tipo de cozinha especificado."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Restaurantes encontrados")
  })
  @GetMapping("/search/by-cuisine")
  public ResponseEntity<List<Restaurant>> findByCuisine(
          @RequestParam
          @Parameter(description = "Tipo de cozinha para busca", required = true)
          String cuisine) {
    return ResponseEntity.ok(searchRestaurantsUseCase.findByCuisine(cuisine));
  }
}
