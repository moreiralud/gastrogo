package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.MakeReservationUseCase;
import com.gastrogo.gastrogo.domain.entity.Reservation;
import com.gastrogo.gastrogo.domain.entity.ReservationStatus;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.repository.ReservationRepository;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            makeReservationUseCase.execute("rest123", "user1", pastDate, 2)
    );
    assertTrue(exception.getMessage().toLowerCase().contains("cannot reserve in the past"));
  }

  @Test
  void shouldFailIfRestaurantNotFound() {
    when(restaurantRepository.findById("restNotFound")).thenReturn(Optional.empty());

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            makeReservationUseCase.execute("restNotFound", "user2", LocalDateTime.now().plusDays(1), 4)
    );
    assertTrue(exception.getMessage().toLowerCase().contains("restaurant not found"));
  }

  @Test
  void shouldFailIfNumberOfPeopleExceedsCapacity() {
    // Simula um restaurante de capacidade 10
    Restaurant restMock = new Restaurant("Pizzaria", "Local", "Italiana", 10, "10h-22h");
    restMock.setId("rest001");

    when(restaurantRepository.findById("rest001")).thenReturn(Optional.of(restMock));

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            makeReservationUseCase.execute("rest001", "user2", LocalDateTime.now().plusDays(1), 15)
    );
    assertTrue(exception.getMessage().toLowerCase().contains("exceeds restaurant capacity"));
  }

  @Test
  void shouldCreateReservationSuccessfully() {
    // Simula um restaurante válido
    Restaurant rest = new Restaurant("Burger House", "Center", "American", 50, "9h-23h");
    rest.setId("restXYZ");
    when(restaurantRepository.findById("restXYZ")).thenReturn(Optional.of(rest));

    // Simula comportamento do repositório: retorna o mesmo objeto salvo
    when(reservationRepository.save(any(Reservation.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

    LocalDateTime futureDate = LocalDateTime.now().plusDays(1);

    // Act
    Reservation result = makeReservationUseCase.execute("restXYZ", "userABC", futureDate, 4);

    // Assert
    verify(reservationRepository, times(1)).save(any(Reservation.class));
    assertEquals("restXYZ", result.getRestaurantId());
    assertEquals("userABC", result.getUserId());
    assertEquals(futureDate, result.getDateTime());
    // Comparar o enum corretamente:
    assertEquals(ReservationStatus.CONFIRMED, result.getStatus());
  }
}
