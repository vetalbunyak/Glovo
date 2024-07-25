package ithillel.ua.glovo;

import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testAddOrder() throws Exception {
        // Prepare request body
        String orderJson = "{ \"status\": \"NEW\" }";

        // Perform POST request
        mockMvc.perform((RequestBuilder) post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(orderJson)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.status").value("NEW"));
    }

    @Test
    public void testGetOrder() throws Exception {
        // Prepare test data
        Order order = new Order();
        order.setStatus("NEW");
        Order savedOrder = orderRepository.save(order);

        // Perform GET request
        mockMvc.perform(get("/orders/" + savedOrder.getId()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status").value("NEW"));
    }
}