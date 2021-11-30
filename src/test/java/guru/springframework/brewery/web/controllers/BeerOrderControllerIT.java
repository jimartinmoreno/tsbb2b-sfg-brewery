package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.Customer;
import guru.springframework.brewery.repositories.CustomerRepository;
import guru.springframework.brewery.web.model.BeerOrderDto;
import guru.springframework.brewery.web.model.BeerOrderPagedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by jt on 2019-03-12.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerOrderControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    CustomerRepository customerRepository;

    UUID customerId;
    UUID orderId;

    @BeforeEach
    void setUp() {
        Customer customer = customerRepository.findAll().get(0);
        customerId = customer.getId();
        System.out.println("customer = " + customerId);
        BeerOrder[] beerOrders = new BeerOrder[0];
        orderId = customer.getBeerOrders().toArray(beerOrders)[0].getId();
        System.out.println("orderId = " + orderId);
    }

    @Test
    void testListOrders() {
        String url = "/api/v1/customers/" + customerId + " /orders";
        BeerOrderPagedList pagedList = testRestTemplate.getForObject(url, BeerOrderPagedList.class);
        System.out.println("pagedList.getContent() = " + pagedList.getContent());
        assertThat(pagedList.getContent()).hasSize(1);
    }

    @Test
    void testOrders() {
        System.out.println("customer = " + customerId);
        System.out.println("orderId = " + orderId);
        String url = "/api/v1/customers/" + customerId + " /orders/" + orderId;
        BeerOrderDto beerOrderDto = testRestTemplate.getForObject(url, BeerOrderDto.class);
        System.out.println("beerOrderDto = " + beerOrderDto);
        assertThat(beerOrderDto.getCustomerRef()).isNotBlank();
    }
}
