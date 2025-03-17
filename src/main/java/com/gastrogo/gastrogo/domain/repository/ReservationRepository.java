package com.gastrogo.gastrogo.domain.repository;

import com.gastrogo.gastrogo.domain.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
  Reservation save(Reservation reservation);
  Optional<Reservation> findById(String id);
  List<Reservation> findByRestaurantId(String restaurantId);
  List<Reservation> findByUserId(String userId);
  // ...
}
