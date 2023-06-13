package br.com.hioktec.placeservice.web.mapper;

import br.com.hioktec.placeservice.api.PlaceRequest;
import br.com.hioktec.placeservice.api.PlaceResponse;
import br.com.hioktec.placeservice.domain.model.Place;

public class PlaceMapper {

  public static PlaceResponse fromDomainModelToResponse (Place place) {
    return new PlaceResponse(place.name(), place.slug(), place.state(), place.createdAt(), place.updatedAt());
  }

  public static Place fromRequestToDomainModel (PlaceRequest placeRequest) {
    return new Place(null, placeRequest.name(), null, placeRequest.state(),
      null, null);
  }

}
