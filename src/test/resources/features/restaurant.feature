Feature: Gestão de Restaurantes

  Scenario: Criar um novo restaurante com sucesso
    Given um restaurante com nome "Cucumber Restaurant", localização "Centro", tipo "Italiana", capacidade 100 e horário de funcionamento "10h-22h"
    When eu crio o restaurante
    Then o restaurante é criado com um ID gerado
    And eu posso recuperar o restaurante com nome "Cucumber Restaurant" e localização "Centro"
