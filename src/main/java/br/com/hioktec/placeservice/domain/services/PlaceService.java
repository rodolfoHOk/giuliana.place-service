package br.com.hioktec.placeservice.domain.services;

import br.com.hioktec.placeservice.domain.exception.ResourceNotFoundException;
import br.com.hioktec.placeservice.domain.model.Place;
import br.com.hioktec.placeservice.domain.repositories.PlaceRepository;
import com.github.slugify.Slugify;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public class PlaceService {

  private final PlaceRepository placeRepository;
  private final Slugify slugify;

  public PlaceService(PlaceRepository placeRepository) {
    this.placeRepository = placeRepository;
    this.slugify = Slugify.builder().build();
  }

  public Mono<Place> create(Place place) {
    var placeToSave = new Place(null, place.name(), slugify.slugify(place.name()), place.state(),
      place.createdAt(), place.updatedAt());
    return placeRepository.save(placeToSave);
  }

  public Flux<Place> list() {
    return placeRepository.findAll();
  }

  public Mono<Place> getById(Long id) {
    return placeRepository.findById(id);
  }

  public Mono<Place> update(Long id, Place place) {
    return placeRepository.findById(id)
      .map(existPlace -> new Place(existPlace.id(), place.name(), slugify.slugify(place.name()), place.state(),
        existPlace.createdAt(), LocalDateTime.now()))
      .flatMap(placeRepository::save);
  }

  public Mono<Void> delete(Long id) {
    return placeRepository.findById(id)
      .switchIfEmpty(Mono.error(new ResourceNotFoundException("Place not found")))
      .flatMap(placeRepository::delete);
  }

}
