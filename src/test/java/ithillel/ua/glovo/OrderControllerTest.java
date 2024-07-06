package ithillel.ua.glovo;

import com.fasterxml.jackson.databind.ObjectMapper;
import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.model.Product;
import ithillel.ua.glovo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setProducts(new ArrayList<>());
        order.setStatus("new");
        orderService.addOrder(order);
    }

    @Test
    public void testGetOrder() throws Exception {
        mockMvc.perform((RequestBuilder) get("/orders/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status").value("new"));
    }

    @Test
    public void testAddOrder() throws Exception {
        Order newOrder = new Order();
        newOrder.setProducts(new ArrayList<>());
        newOrder.setStatus("pending");

        ResultActions pending = mockMvc.perform((RequestBuilder) post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(newOrder))))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status").value("pending"));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        order.setStatus("completed");

        ResultActions completed = mockMvc.perform((RequestBuilder) put("/orders/{id}", order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(order))))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status").value("completed"));
    }

    @Test
    public void testAddProductToOrder() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(10.0);

        mockMvc.perform((RequestBuilder) patch("/orders/{orderId}/products", order.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(product))))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.products[0].name").value("Product 1"));
    }

    @Test
    public void testRemoveProductFromOrder() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(10.0);
        orderService.addProductToOrder(order.getId(), product);

        mockMvc.perform((RequestBuilder) delete("/orders/{orderId}/products/{productId}", order.getId(), product.getId()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.products").isEmpty());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform((RequestBuilder) delete("/orders/{id}", order.getId()))
                .andExpect(status().isOk());
    }
}