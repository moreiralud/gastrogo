package com.gastrogo.gastrogo.application.service.entity;

import com.gastrogo.gastrogo.domain.entity.Review;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

  @Test
  void testCreateReview() {
    // Cria uma review válida
    Review r = new Review("rest01", "userABC", 4.5, "Great place!");

    // Verifica se os valores estão corretos
    assertEquals("rest01", r.getRestaurantId());
    assertEquals("userABC", r.getUserId());
    assertEquals(4.5, r.getRating(), 0.0001);
    assertEquals("Great place!", r.getComment());
  }

  @Test
  void testSettersAndGetters() {
    // Construtor vazio (se você tiver um @NoArgsConstructor)
    Review r = new Review();

    r.setRestaurantId("rest99");
    r.setUserId("userXYZ");
    r.setRating(3.0);
    r.setComment("It was okay.");

    assertEquals("rest99", r.getRestaurantId());
    assertEquals("userXYZ", r.getUserId());
    assertEquals(3.0, r.getRating(), 0.0001);
    assertEquals("It was okay.", r.getComment());
  }

  @Test
  void testInvalidRating() {
    // Caso queira validar rating fora do range, mas isso depende se
    // a lógica está na própria entidade Review ou no EvaluateRestaurantService.
    // Se a entidade contiver validação no setter, podemos testar aqui:
    Review r = new Review();
    // Suponha que no setter do rating haja algo tipo:
    // if (value < 0 || value > 5) throw new IllegalArgumentException("Invalid rating");
    try {
      r.setRating(6.0);
      fail("Deveria ter lançado exceção para rating > 5");
    } catch (IllegalArgumentException e) {
      assertTrue(e.getMessage().contains("Invalid rating"));
    }
  }
}
