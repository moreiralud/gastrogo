package com.gastrogo.gastrogo.domain.adapters.persistence;

import com.gastrogo.gastrogo.domain.entity.Reservation;
import com.gastrogo.gastrogo.domain.repository.ReservationRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationMongoRepository extends
        MongoRepository<Reservation, String>,
        ReservationRepository
{
  List<Reservation> findByRestaurantId(String restaurantId);
  List<Reservation> findByUserId(String userId);

}
