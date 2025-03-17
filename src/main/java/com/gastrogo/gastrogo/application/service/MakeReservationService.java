package com.gastrogo.gastrogo.application.service;

import com.gastrogo.gastrogo.application.usecase.MakeReservationUseCase;
import com.gastrogo.gastrogo.domain.entity.Reservation;
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
    // 1) Verifica se a data/hora está no futuro
    if (dateTime.isBefore(LocalDateTime.now())) {
      throw new IllegalArgumentException("Cannot make a reservation in the past.");
    }
    // 2) Verifica se o restaurante existe
    Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + restaurantId));

    // 3) (Opcional) Verificar a capacidade do restaurante e se já há reservas
    // Exemplo bem simples:
    //  - Fazer uma consulta de reservas naquele horário e recusar se exceder a capacidade
    //  - Dependendo da lógica, poderia checar intervalos de tempo, etc.

    // Cria a reserva e salva
    Reservation reservation = new Reservation(restaurantId, userId, dateTime, numberOfPeople);
    return reservationRepository.save(reservation);
  }
}
