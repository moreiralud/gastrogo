package com.gastrogo.gastrogo.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "restaurants")
public class Restaurant {

  @Id
  private String id;
  private String name;
  private String location;
  private String cuisineType;
  private int capacity;

  // Exemplo: horário de funcionamento simples (8h-22h), apenas armazenando string
  private String openingHours;

  // Pode conter avaliações (reviews), mas depende se quer armazenar em subdocument
  // ou em coleção separada. Simples: guardamos IDs de reviews ou guardamos a lista.
  private List<Review> reviews = new ArrayList<>();

  public Restaurant(String name, String location, String cuisineType, int capacity, String openingHours) {
    this.name = name;
    this.location = location;
    this.cuisineType = cuisineType;
    this.capacity = capacity;
    this.openingHours = openingHours;
  }

  public double getAverageRating() {
    if (reviews.isEmpty()) {
      return 0.0;
    }
    return reviews.stream()
            .mapToDouble(Review::getRating)
            .average()
            .orElse(0.0);
  }

  public void addReview(Review review) {
    this.reviews.add(review);
  }
}
