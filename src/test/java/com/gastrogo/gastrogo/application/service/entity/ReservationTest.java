package com.gastrogo.gastrogo.application.service.entity;

import com.gastrogo.gastrogo.domain.entity.Reservation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class ReservationTest {

  @Test
  void testCancelReservation() {
    Reservation reservation = new Reservation("rest123", "user456", LocalDateTime.now().plusDays(1), 4);
    assertEquals("CONFIRMED", reservation.getStatus());

    reservation.cancel();
    assertEquals("CANCELLED", reservation.getStatus());
  }
}
