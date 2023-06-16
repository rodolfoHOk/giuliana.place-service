package br.com.hioktec.placeservice;

import br.com.hioktec.placeservice.api.PlaceRequest;
import br.com.hioktec.placeservice.api.PlaceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaceServiceApplicationTests {

	@Autowired
	WebTestClient webTestClient;

	@Test
	public void testCreatePlaceSuccess() {
		var name = "Valid Name";
		var state = "Valid State";
		var expectedSlug = "valid-name";

		webTestClient
			.post()
			.uri("/places")
			.bodyValue(new PlaceRequest(name, state))
			.exchange()
			.expectBody()
			.jsonPath("name").isEqualTo(name)
			.jsonPath("state").isEqualTo(state)
			.jsonPath("slug").isEqualTo(expectedSlug)
			.jsonPath("createdAt").isNotEmpty()
			.jsonPath("updatedAt").isNotEmpty()
		;
	}

	@Test
	public void testCreatePlaceFailure() {
		var name = "";
		var state = "";

		webTestClient
			.post()
			.uri("/places")
			.bodyValue(new PlaceRequest(name, state))
			.exchange()
			.expectStatus().isBadRequest()
		;
	}

	@Test
	public void testListPlacesSuccess() {
		seedOnePlace("place1", "state1");
		seedOnePlace("place2", "state2");

		webTestClient
			.get()
			.uri("/places")
			.exchange()
			.expectStatus().isOk()
			.expectBodyList(PlaceResponse.class).hasSize(2);
	}

	@Test
	public void testGetPlaceByIdNotFound() {
		webTestClient
			.get()
			.uri("/places/10")
			.exchange()
			.expectStatus().isNotFound();
	}

	@Test
	public void testGetPlaceByIdOk() {
		seedOnePlace("place1", "state1");

		webTestClient
			.get()
			.uri("/places/1")
			.exchange()
			.expectStatus().isOk()
			.expectBody(PlaceResponse.class);
	}

	private void seedOnePlace(String name, String state) {
		webTestClient
			.post()
			.uri("/places")
			.bodyValue(new PlaceRequest(name, state))
			.exchange();
	}

}
