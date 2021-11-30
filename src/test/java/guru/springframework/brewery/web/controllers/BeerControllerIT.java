package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.Beer;
import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.BeerRepository;
import guru.springframework.brewery.repositories.CustomerRepository;
import guru.springframework.brewery.web.model.BeerPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    BeerRepository beerRepository;

    UUID beerId;

    @BeforeEach
    void setUp() {
        beerId = beerRepository.findAll().get(0).getId();
        System.out.println("beerId = " + beerId);
    }

    @Test
    void testListBeers() {
        /**
         * Revisar la clase BeerPagedList donde se ha añadido un constructor anotado con
         * @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) para evitar una excepción que se produce al intentar
         */
        BeerPagedList beerPagedList = restTemplate.getForObject("/api/v1/beer", BeerPagedList.class);
        assertThat(beerPagedList.getContent()).hasSize(3);
    }

    @Test
    void testGetBeer() {
        Beer beer2 = restTemplate.getForObject("/api/v1/beer/{beerId}", Beer.class, beerId.toString());
        assertThat(beer2.getBeerName()).isEqualTo("Mango Bobs");
    }
}
