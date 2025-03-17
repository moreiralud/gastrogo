package com.gastrogo.gastrogo.domain.adapters.controller;

import com.gastrogo.gastrogo.application.usecase.MakeReservationUseCase;
import com.gastrogo.gastrogo.application.usecase.ManageReservationUseCase;
import com.gastrogo.gastrogo.domain.entity.Reservation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Endpoints para gerenciar reservas de restaurantes.
 */
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final MakeReservationUseCase makeReservationUseCase;
  private final ManageReservationUseCase manageReservationUseCase;

  /**
   * Cria uma nova reserva.
   * Exemplo de uso de uma classe DTO para receber dados.
   */
  @PostMapping
  public ResponseEntity<Reservation> createReservation(@RequestBody CreateReservationRequest request) {
    Reservation reservation = makeReservationUseCase.execute(
            request.getRestaurantId(),
            request.getUserId(),
            request.getDateTime(),
            request.getNumberOfPeople()
    );
    return ResponseEntity.ok(reservation);
  }

  /**
   * Cancela uma reserva.
   * PATCH é semântico quando alteramos parcialmente um recurso (aqui, o status).
   */
  @PatchMapping("/{id}/cancel")
  public ResponseEntity<Reservation> cancelReservation(@PathVariable String id) {
    try {
      Reservation canceled = manageReservationUseCase.cancelReservation(id);
      return ResponseEntity.ok(canceled);
    } catch (IllegalArgumentException e) {
      // Se a reserva não for encontrada ou alguma lógica falhar.
      return ResponseEntity.notFound().build();
    }
  }

  /**
   * Conclui uma reserva (marca como COMPLETED).
   */
  @PatchMapping("/{id}/complete")
  public ResponseEntity<Reservation> completeReservation(@PathVariable String id) {
    try {
      Reservation completed = manageReservationUseCase.completeReservation(id);
      return ResponseEntity.ok(completed);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  // Caso você tenha métodos para buscar reservas por usuário/restaurante,
  // eles podem estar em outro UseCase ou repositório. Exemplo:
  // (dependendo de como você estruturou o domain e usecases).
}

/**
 * DTO para criação de reserva, evita expor a entidade inteira diretamente.
 * Facilita validações específicas e deixa o controller mais claro.
 */
@Data
class CreateReservationRequest {
  private String restaurantId;
  private String userId;
  private LocalDateTime dateTime;
  private int numberOfPeople;
}
