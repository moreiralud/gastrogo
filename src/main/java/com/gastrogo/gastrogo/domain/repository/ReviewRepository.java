package com.gastrogo.gastrogo.domain.repository;

import com.gastrogo.gastrogo.domain.entity.Review;

import java.util.List;

public interface ReviewRepository {
  Review save(Review review);
  List<Review> findByRestaurantId(String restaurantId);
  // ...
}
