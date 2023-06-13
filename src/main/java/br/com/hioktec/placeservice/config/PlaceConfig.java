package br.com.hioktec.placeservice.config;

import br.com.hioktec.placeservice.domain.repositories.PlaceRepository;
import br.com.hioktec.placeservice.domain.services.PlaceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

@Configuration
@EnableR2dbcAuditing
public class PlaceConfig {

  @Bean
  PlaceService placeService(PlaceRepository placeRepository) {
    return new PlaceService(placeRepository);
  }

}
