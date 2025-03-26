package com.gastrogo.gastrogo.domain.adapters.controller;

import com.gastrogo.gastrogo.application.usecase.MakeReservationUseCase;
import com.gastrogo.gastrogo.application.usecase.ManageReservationUseCase;
import com.gastrogo.gastrogo.domain.entity.Reservation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

  private final MakeReservationUseCase makeReservationUseCase;
  private final ManageReservationUseCase manageReservationUseCase;

  @Operation(summary = "Cria uma nova reserva", description = "Cria uma nova reserva de restaurante com os dados fornecidos.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Reserva criada com sucesso"),
          @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
  })
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

  @Operation(summary = "Cancela uma reserva", description = "Cancela a reserva identificada pelo ID, alterando seu status para CANCELLED.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Reserva cancelada com sucesso"),
          @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
  })
  @PatchMapping("/{id}/cancel")
  public ResponseEntity<Reservation> cancelReservation(@PathVariable String id) {
    try {
      Reservation canceled = manageReservationUseCase.cancelReservation(id);
      return ResponseEntity.ok(canceled);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @Operation(summary = "Conclui uma reserva", description = "Marca a reserva identificada pelo ID como COMPLETED, indicando que a reserva foi finalizada.")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Reserva concluída com sucesso"),
          @ApiResponse(responseCode = "404", description = "Reserva não encontrada")
  })
  @PatchMapping("/{id}/complete")
  public ResponseEntity<Reservation> completeReservation(@PathVariable String id) {
    try {
      Reservation completed = manageReservationUseCase.completeReservation(id);
      return ResponseEntity.ok(completed);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.notFound().build();
    }
  }
}

@Data
class CreateReservationRequest {
  private String restaurantId;
  private String userId;
  private LocalDateTime dateTime;
  private int numberOfPeople;
}
