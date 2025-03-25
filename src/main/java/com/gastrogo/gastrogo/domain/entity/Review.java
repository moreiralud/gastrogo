package com.gastrogo.gastrogo.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "reviews")
public class Review {

  @Id
  private String id;
  private String restaurantId;
  private String userId;
  private double rating;
  private String comment;

  public Review(String restaurantId, String userId, double rating, String comment) {
    this.restaurantId = restaurantId;
    this.userId = userId;
    setRating(rating);
    this.comment = comment;
  }

  public void setRating(double rating) {
    if (rating < 0 || rating > 5) {
      throw new IllegalArgumentException("Invalid rating: rating must be between 0 and 5.");
    }
    this.rating = rating;
  }
}
