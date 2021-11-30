package guru.springframework.brewery.events;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.brewery.domain.BeerOrder;
import guru.springframework.brewery.domain.OrderStatusEnum;
import guru.springframework.brewery.web.model.BeerOrderDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestToUriTemplate;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @RestClientTest Annotation for a Spring rest client test that focuses only on beans that use RestTemplateBuilder.
 * Using this annotation will disable full auto-configuration and instead apply only configuration relevant to rest client tests
 */
//@Disabled // utility for manual testing
@RestClientTest(BeerOrderStatusChangeEventListener.class)
@AutoConfigureJsonTesters
class BeerOrderStatusChangeEventListenerMockTest {

    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    BeerOrderStatusChangeEventListener listener;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        String detailsString = objectMapper.writeValueAsString(getValidBeerOrderDtoList());
        this.mockServer.expect(requestToUriTemplate("http://localhost:8080/update",
                        "45772dd4-3e82-4d49-9951-4b4d8d3a0a1b"))
                .andRespond(withSuccess(detailsString, MediaType.APPLICATION_JSON));
    }

    @Test
    void listen() {

        BeerOrder beerOrder = BeerOrder.builder()
                .orderStatus(OrderStatusEnum.READY)
                .orderStatusCallbackUrl("http://localhost:8080/update")
                .createdDate(Timestamp.valueOf(LocalDateTime.now()))
                .version(0L)
                .orderStatus(OrderStatusEnum.NEW)
                .customerRef("AAAAAa")
                .build();

        BeerOrderStatusChangeEvent event = new BeerOrderStatusChangeEvent(beerOrder, OrderStatusEnum.NEW);

        listener.listen(event);

        System.out.println("listen <<<< ");
    }

    List<BeerOrderDto> getValidBeerOrderDtoList() {
        return List.of(BeerOrderDto.builder()
                .id(UUID.fromString("7aa9d132-4c66-11ec-81d3-0242ac130003"))
                .build());
    }
}