package br.com.hioktec.placeservice;

import br.com.hioktec.placeservice.api.PlaceRequest;
import br.com.hioktec.placeservice.api.PlaceResponse;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PlaceServiceApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Test
	@Order(1)
	public void testCreatePlaceFailure() {
		var name = "";
		var state = "";

		webTestClient
			.post()
			.uri("/places")
			.bodyValue(new PlaceRequest(name, state))
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Test
	@Order(2)
	public void testCreatePlaceSuccess() {
		var name = "Valid Name";
		var state = "Valid State";
		var expectedSlug = "valid-name";

		webTestClient
			.post()
			.uri("/places")
			.bodyValue(new PlaceRequest(name, state))
			.exchange()
			.expectStatus().isCreated()
			.expectBody()
			.jsonPath("name").isEqualTo(name)
			.jsonPath("state").isEqualTo(state)
			.jsonPath("slug").isEqualTo(expectedSlug)
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty();
	}

	@Test
	@Order(3)
	public void testListPlacesSuccess() {
		webTestClient
			.get()
			.uri("/places")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(PlaceResponse.class).hasSize(1);
	}

	@Test
	@Order(4)
	public void testGetPlaceByIdNotFound() {
		webTestClient
			.get()
			.uri("/places/10")
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	@Order(5)
	public void testGetPlaceByIdOk() {
		webTestClient
			.get()
			.uri("/places/1")
			.exchange()
			.expectStatus().isOk()
			.expectBody(PlaceResponse.class);
	}

	@Test
	@Order(6)
	public void testUpdatePlaceNotFound() {
		var name = "Valid Name";
		var state = "Valid State";

		webTestClient
			.put()
			.uri("/places/10")
			.bodyValue(new PlaceRequest(name, state))
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	@Order(7)
	public void testUpdatePlaceBaqRequest() {
		var name = "";
		var state = "";

		webTestClient
			.put()
			.uri("/places/1")
			.bodyValue(new PlaceRequest(name, state))
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Test
	@Order(8)
	public void testUpdatePlaceOk() {
		var name = "Valid Name Update";
		var state = "Valid State Update";
		var expectedSlug = "valid-name-update";

		webTestClient
			.put()
			.uri("/places/1")
			.bodyValue(new PlaceRequest(name, state))
			.exchange()
			.expectStatus().isOk()
			.expectBody()
			.jsonPath("name").isEqualTo(name)
			.jsonPath("state").isEqualTo(state)
			.jsonPath("slug").isEqualTo(expectedSlug)
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty();
	}

	@Test
	@Order(9)
	public void testDeletePlaceBaqRequest() {
		webTestClient
			.delete()
			.uri("/places/10")
			.exchange()
			.expectStatus().isBadRequest();
	}

	@Test
	@Order(10)
	public void testDeletePlaceNoContent() {
		webTestClient
			.delete()
			.uri("/places/1")
			.exchange()
			.expectStatus().isNoContent();
	}

}
