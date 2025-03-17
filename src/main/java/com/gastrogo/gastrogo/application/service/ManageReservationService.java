package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.ManageReservationUseCase;
import com.gastrogo.gastrogo.domain.entity.Reservation;
import com.gastrogo.gastrogo.domain.entity.ReservationStatus;
import com.gastrogo.gastrogo.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageReservationService implements ManageReservationUseCase {

  private final ReservationRepository reservationRepository;

  @Override
  public Reservation cancelReservation(String reservationId) {
    Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));
    reservation.cancel();  // status = CANCELLED
    return reservationRepository.save(reservation);
  }

  @Override
  public Reservation completeReservation(String reservationId) {
    Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new IllegalArgumentException("Reservation not found: " + reservationId));
    // Vamos supor que você criou um método 'complete()' em Reservation
    reservation.setStatus(ReservationStatus.COMPLETED );
    return reservationRepository.save(reservation);
  }
}
