package com.gastrogo.gastrogo.application.usecase;


import com.gastrogo.gastrogo.domain.entity.Reservation;

public interface ManageReservationUseCase {
  Reservation cancelReservation(String reservationId);
  Reservation completeReservation(String reservationId);
  // Podem ter outros métodos, ex.: reprogramar data, etc.
}
