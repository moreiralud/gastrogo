package com.gastrogo.gastrogo.domain.adapters.controller;

import com.gastrogo.gastrogo.application.usecase.EvaluateRestaurantUseCase;
import com.gastrogo.gastrogo.domain.entity.Review;
import com.gastrogo.gastrogo.domain.repository.ReviewRepository;
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
  @PostMapping
  public ResponseEntity<Review> createReview(@RequestBody Review review) {
    // Aqui, passamos a entidade diretamente ou um DTO, conforme preferir.
    try {
      Review saved = evaluateRestaurantUseCase.execute(review);
      return ResponseEntity.ok(saved);
    } catch (IllegalArgumentException e) {
      // Caso a nota seja inválida, restaurante inexistente, etc.
      return ResponseEntity.badRequest().build();
    }
  }

  /**
   * Lista avaliações de um restaurante.
   */
  @GetMapping("/by-restaurant/{restaurantId}")
  public ResponseEntity<List<Review>> listByRestaurant(@PathVariable String restaurantId) {
    List<Review> reviews = reviewRepository.findByRestaurantId(restaurantId);
    return ResponseEntity.ok(reviews);
  }
}
