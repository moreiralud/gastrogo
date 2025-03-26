package com.gastrogo.gastrogo.application.usecase;

import com.gastrogo.gastrogo.domain.entity.Reservation;

import java.time.LocalDateTime;

public interface MakeReservationUseCase {
  Reservation execute(String restaurantId, String userId, LocalDateTime dateTime, int numberOfPeople);
}
