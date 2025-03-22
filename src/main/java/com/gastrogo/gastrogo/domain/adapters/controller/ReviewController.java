package com.gastrogo.gastrogo.domain.adapters.controller;

import com.gastrogo.gastrogo.application.usecase.EvaluateRestaurantUseCase;
import com.gastrogo.gastrogo.domain.entity.Review;
import com.gastrogo.gastrogo.domain.repository.ReviewRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

  private final EvaluateRestaurantUseCase evaluateRestaurantUseCase;
  private final ReviewRepository reviewRepository;
  // ou se quiser, injete um "SearchReviewUseCase" se tiver

  /**
   * Cria uma avaliação para um restaurante.
   */
  @Operation(
          summary = "Cria uma avaliação",
          description = "Cria uma nova avaliação para um restaurante com os dados fornecidos e retorna a avaliação criada."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Avaliação criada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos ou nota fora do intervalo permitido")
  })
  @PostMapping
  public ResponseEntity<Review> createReview(
          @RequestBody
          @Parameter(description = "Dados da avaliação", required = true)
          Review review) {
    try {
      Review saved = evaluateRestaurantUseCase.execute(review);
      return ResponseEntity.ok(saved);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Lista avaliações de um restaurante.
   */
  @Operation(
          summary = "Lista avaliações por restaurante",
          description = "Retorna uma lista de avaliações associadas ao restaurante identificado pelo ID fornecido."
  )
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Avaliações retornadas com sucesso")
  })
  @GetMapping("/by-restaurant/{restaurantId}")
  public ResponseEntity<List<Review>> listByRestaurant(
          @PathVariable
          @Parameter(description = "ID do restaurante", required = true)
          String restaurantId) {
    List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
    return ResponseEntity.ok(reviews);
  }
}
