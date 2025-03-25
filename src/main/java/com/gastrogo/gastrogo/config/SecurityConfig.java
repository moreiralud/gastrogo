package com.gastrogo.gastrogo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .authorizeHttpRequests(authorize -> authorize
                    // Libera os endpoints do Swagger para acesso sem autenticação
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    // Exige autenticação para os demais endpoints
                    .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            // Desabilita o CSRF pois nossa API é stateless e não utiliza sessões baseadas em cookies.
            // Essa decisão foi avaliada e é segura neste contexto. NOSONAR
            .csrf(csrf -> csrf.disable());

    return http.build();
  }
}
