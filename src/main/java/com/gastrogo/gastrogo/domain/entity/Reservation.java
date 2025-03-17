package com.gastrogo.gastrogo.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Document(collection = "reservations")
public class Reservation {
  @Id
  private String id;

  private String restaurantId;
  private String userId;
  private LocalDateTime dateTime;
  private int numberOfPeople;

  // Agora, ao invés de String, usamos o enum:
  private ReservationStatus status;

  public Reservation(String restaurantId, String userId, LocalDateTime dateTime, int numberOfPeople) {
    this.restaurantId = restaurantId;
    this.userId = userId;
    this.dateTime = dateTime;
    this.numberOfPeople = numberOfPeople;
    this.status = ReservationStatus.CONFIRMED;
  }

  // Método de cancelamento
  public void cancel() {
    this.status = ReservationStatus.CANCELLED;
  }

  // Método de concluir reserva (opcional)
  public void complete() {
    this.status = ReservationStatus.COMPLETED;
  }
}
