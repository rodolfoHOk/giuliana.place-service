package br.com.hioktec.placeservice.domain.services;

import br.com.hioktec.placeservice.domain.model.Place;
import br.com.hioktec.placeservice.domain.repositories.PlaceRepository;
import com.github.slugify.Slugify;
import reactor.core.publisher.Mono;

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

}
