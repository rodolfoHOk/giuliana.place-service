package br.com.hioktec.placeservice.domain.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public record Place(
  @Id Long id,
  String name,
  String slug,
  String state,
  @CreatedDate LocalDateTime createdAt,
  @LastModifiedDate LocalDateTime updatedAt
) {

}
