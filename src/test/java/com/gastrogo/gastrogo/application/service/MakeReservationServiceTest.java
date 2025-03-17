package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.MakeReservationUseCase;
import com.gastrogo.gastrogo.domain.entity.Reservation;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.repository.ReservationRepository;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MakeReservationServiceTest {

  private ReservationRepository reservationRepository;
  private RestaurantRepository restaurantRepository;
  private MakeReservationUseCase makeReservationUseCase;

  @BeforeEach
  void setUp() {
    reservationRepository = Mockito.mock(ReservationRepository.class);
    restaurantRepository = Mockito.mock(RestaurantRepository.class);
    makeReservationUseCase = new MakeReservationService(reservationRepository, restaurantRepository);
  }

  @Test
  void shouldNotAllowReservationInPastDate() {
    LocalDateTime pastDate = LocalDateTime.now().minusDays(1);

    try {
      makeReservationUseCase.execute("rest123", "user1", pastDate, 2);
      assert false : "Deveria ter lançado IllegalArgumentException";
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("Cannot reserve in the past");
    }
  }

  @Test
  void shouldFailIfRestaurantNotFound() {
    when(restaurantRepository.findById("restNotFound")).thenReturn(Optional.empty());

    try {
      makeReservationUseCase.execute("restNotFound", "user2", LocalDateTime.now().plusDays(1), 4);
      assert false : "Deveria ter lançado exceção para restaurante inexistente";
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("Restaurant not found");
    }
  }

  @Test
  void shouldFailIfNumberOfPeopleExceedsCapacity() {
    // Simular um restaurante de capacidade 10
    Restaurant restMock = new Restaurant("Pizzaria", "Local", "Italiana", 10, "10h-22h");
    restMock.setId("rest001");

    when(restaurantRepository.findById("rest001")).thenReturn(Optional.of(restMock));

    try {
      makeReservationUseCase.execute("rest001", "user2", LocalDateTime.now().plusDays(1), 15);
      assert false : "Excedeu capacidade, deveria falhar";
    } catch (IllegalArgumentException e) {
      assert e.getMessage().contains("Exceeds restaurant capacity");
    }
  }

  @Test
  void shouldCreateReservationSuccessfully() {
    // Simular restaurante válido
    Restaurant rest = new Restaurant("Burger House", "Center", "American", 50, "9h-23h");
    rest.setId("restXYZ");
    when(restaurantRepository.findById("restXYZ")).thenReturn(Optional.of(rest));

    // Simular comportamento do repo de reserva
    when(reservationRepository.save(any(Reservation.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

    // Act
    Reservation result = makeReservationUseCase.execute("restXYZ", "userABC", futureDate, 4);

    // Assert
    verify(reservationRepository, times(1)).save(any(Reservation.class));
    assert "restXYZ".equals(result.getRestaurantId());
    assert "userABC".equals(result.getUserId());
    assert result.getDateTime().equals(futureDate);
    assert result.getStatus().equals("CONFIRMED");
  }
}
