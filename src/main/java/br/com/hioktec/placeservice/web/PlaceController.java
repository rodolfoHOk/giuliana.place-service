package br.com.hioktec.placeservice.web;

import br.com.hioktec.placeservice.api.PlaceRequest;
import br.com.hioktec.placeservice.api.PlaceResponse;
import br.com.hioktec.placeservice.domain.services.PlaceService;
import br.com.hioktec.placeservice.web.mapper.PlaceMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {

  private final PlaceService placeService;

  public PlaceController(PlaceService placeService) {
    this.placeService = placeService;
  }

  @PostMapping
  public ResponseEntity<Mono<PlaceResponse>> create(@Valid @RequestBody PlaceRequest requestBody) {
    var createdPlace = placeService.create(PlaceMapper.fromRequestToDomainModel(requestBody))
      .map(PlaceMapper::fromDomainModelToResponse);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdPlace);
  }

}
