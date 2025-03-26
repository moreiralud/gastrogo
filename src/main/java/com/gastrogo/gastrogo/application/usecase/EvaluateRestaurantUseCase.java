package com.gastrogo.gastrogo.application.usecase;


import com.gastrogo.gastrogo.domain.entity.Review;

public interface EvaluateRestaurantUseCase {
  Review execute(Review review);
}
