package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.EvaluateRestaurantUseCase;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.entity.Review;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import com.gastrogo.gastrogo.domain.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluateRestaurantService implements EvaluateRestaurantUseCase {

  private final ReviewRepository reviewRepository;
  private final RestaurantRepository restaurantRepository;

  @Override
  public Review execute(Review review) {
    // 1) Validação de nota
    if (review.getRating() < 0 || review.getRating() > 5) {
      throw new IllegalArgumentException("Rating must be between 0 and 5.");
    }
    // 2) Verifica se o restaurante existe
    Restaurant restaurant = restaurantRepository.findById(review.getRestaurantId())
            .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + review.getRestaurantId()));

    // 3) Salva a review
    Review savedReview = reviewRepository.save(review);

    // 4) Se quisermos manter lista de reviews dentro do Restaurant (subdocumento):
    restaurant.getReviews().add(savedReview);
    restaurantRepository.save(restaurant);

    return savedReview;
  }
}
