package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.MakeReservationUseCase;
import com.gastrogo.gastrogo.domain.entity.Reservation;
import com.gastrogo.gastrogo.domain.entity.ReservationStatus;
import com.gastrogo.gastrogo.domain.entity.Restaurant;
import com.gastrogo.gastrogo.domain.repository.ReservationRepository;
import com.gastrogo.gastrogo.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MakeReservationService implements MakeReservationUseCase {

  private final ReservationRepository reservationRepository;
  private final RestaurantRepository restaurantRepository;

  @Override
  public Reservation execute(String restaurantId, String userId, LocalDateTime dateTime, int numberOfPeople) {
    // Validação: a data deve ser no futuro
    if (dateTime.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Cannot reserve in the past");
    }

    // Validação: restaurante deve existir
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + restaurantId));

    // Validação: número de pessoas não pode exceder a capacidade
    if (numberOfPeople > restaurant.getCapacity()) {
      throw new IllegalArgumentException("Exceeds restaurant capacity");
    }

    // Cria a reserva, definindo o status como CONFIRMED
    Reservation reservation = new Reservation(restaurantId, userId, dateTime, numberOfPeople);
    reservation.setStatus(ReservationStatus.CONFIRMED);

    return reservationRepository.save(reservation);
  }
}
