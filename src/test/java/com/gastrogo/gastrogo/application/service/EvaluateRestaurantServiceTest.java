package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.EvaluateRestaurantUseCase;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.entity.Review;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import com.gastrogo.gastrogo.domain.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EvaluateRestaurantServiceTest {

  private ReviewRepository reviewRepository;
  private RestaurantRepository restaurantRepository;
  private EvaluateRestaurantUseCase evaluateRestaurantUseCase;

  @BeforeEach
  void setUp() {
    reviewRepository = Mockito.mock(ReviewRepository.class);
    restaurantRepository = Mockito.mock(RestaurantRepository.class);
    evaluateRestaurantUseCase = new EvaluateRestaurantService(reviewRepository, restaurantRepository);
  }

  @Test
  void shouldFailWhenRatingOutOfRange() {
    Review r = new Review();
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      r.setRating(6.0);
    });
    assertTrue(exception.getMessage().toLowerCase().contains("rating must be between 0 and 5"));
  }


  @Test
  void shouldFailIfRestaurantNotFound() {
    when(restaurantRepository.findById("restNotFound")).thenReturn(Optional.empty());

    Review review = new Review("restNotFound", "userY", 4.0, "Nice");
    try {
      evaluateRestaurantUseCase.execute(review);
      assert false : "Deveria falhar, restaurante não existe";
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("Restaurant not found");
    }
  }

  @Test
  void shouldCreateReviewAndAttachToRestaurant() {
    // Mock do restaurante
    Restaurant rest = new Restaurant("Test Restaurant", "Location", "Fusion", 100, "9h-22h");
    rest.setId("restOK");
    when(restaurantRepository.findById("restOK")).thenReturn(Optional.of(rest));

    // Mock do reviewRepo
    when(reviewRepository.save(any(Review.class)))
            .thenAnswer(inv -> {
              Review r = inv.getArgument(0);
              r.setId("rev123");
              return r;
            });

    // Act
    Review input = new Review("restOK", "userZ", 4.5, "Great place!");
    Review saved = evaluateRestaurantUseCase.execute(input);

    // Assert
    verify(reviewRepository, times(1)).save(input);
    assert saved.getId().equals("rev123");
    assert saved.getRating() == 4.5;
  }
}
