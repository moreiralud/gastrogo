package com.gastrogo.gastrogo.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@Document(collection = "reviews")
public class Review {

  @Id
  private String id;
  private String restaurantId;
  private String userId;
  private double rating;   // ex: 0..5
  private String comment;

  public Review(String restaurantId, String userId, double rating, String comment) {
    this.restaurantId = restaurantId;
    this.userId = userId;
    this.rating = rating;
    this.comment = comment;
  }
}
