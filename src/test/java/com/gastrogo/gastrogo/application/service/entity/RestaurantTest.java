package com.gastrogo.gastrogo.application.service.entity;

import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.entity.Review;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {

  @Test
  void testGetAverageRating() {
    Restaurant r = new Restaurant("Pizza Place", "Center", "Italian", 50, "8h-22h");

    r.addReview(new Review("rest1", "user1", 5.0, "Excellent!"));
    r.addReview(new Review("rest1", "user2", 3.0, "Ok"));

    double avg = r.getAverageRating();
    assertEquals(4.0, avg, 0.001);
  }
}
