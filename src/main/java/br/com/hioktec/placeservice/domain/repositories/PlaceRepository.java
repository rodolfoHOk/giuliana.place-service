package br.com.hioktec.placeservice.domain.repositories;

import br.com.hioktec.placeservice.domain.model.Place;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PlaceRepository extends ReactiveCrudRepository<Place, Long> {

}
