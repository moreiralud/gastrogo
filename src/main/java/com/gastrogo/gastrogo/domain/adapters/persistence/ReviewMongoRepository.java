package com.gastrogo.gastrogo.domain.adapters.persistence;

import com.gastrogo.gastrogo.domain.entity.Review;
import com.gastrogo.gastrogo.domain.repository.ReviewRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewMongoRepository extends
        MongoRepository<Review, String>,
        ReviewRepository
{
  List<Review> findByRestaurantId(String restaurantId);
}
