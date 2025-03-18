package com.gastrogo.gastrogo.application.service.entity;

import com.gastrogo.gastrogo.domain.entity.Reservation;
import com.gastrogo.gastrogo.domain.entity.ReservationStatus;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class ReservationTest {

  @Test
  void testCancelReservation() {
    Reservation reservation = new Reservation("rest123", "user456", LocalDateTime.now().plusDays(1), 4);
    // Compare com o enum diretamente:
    assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());

    reservation.cancel();
    assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
  }
}
