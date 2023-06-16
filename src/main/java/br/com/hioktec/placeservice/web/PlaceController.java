package br.com.hioktec.placeservice.web;

import br.com.hioktec.placeservice.api.PlaceRequest;
import br.com.hioktec.placeservice.api.PlaceResponse;
import br.com.hioktec.placeservice.domain.exception.ResourceNotFoundException;
import br.com.hioktec.placeservice.domain.services.PlaceService;
import br.com.hioktec.placeservice.web.mapper.PlaceMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/places")
public class PlaceController {

  private final PlaceService placeService;

  public PlaceController(PlaceService placeService) {
    this.placeService = placeService;
  }

  @PostMapping
  public Mono<ResponseEntity<PlaceResponse>> create(@Valid @RequestBody PlaceRequest requestBody) {
    return placeService.create(PlaceMapper.fromRequestToDomainModel(requestBody))
      .map(PlaceMapper::fromDomainModelToResponse)
      .map(place -> ResponseEntity.status(HttpStatus.CREATED).body(place));
  }

  @GetMapping
  public Flux<PlaceResponse> list() {
    return placeService.list()
      .map(PlaceMapper::fromDomainModelToResponse);
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<PlaceResponse>> getById(@PathVariable Long id) {
    return placeService.getById(id)
      .map(PlaceMapper::fromDomainModelToResponse)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<PlaceResponse>> update(@PathVariable Long id, @Valid @RequestBody PlaceRequest requestBody) {
    return placeService.update(id, PlaceMapper.fromRequestToDomainModel(requestBody))
      .map(PlaceMapper::fromDomainModelToResponse)
      .map(ResponseEntity::ok)
      .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
    return placeService.delete(id)
      .map(ResponseEntity::ok)
      .onErrorReturn(ResponseEntity.badRequest().build());
  }

}
