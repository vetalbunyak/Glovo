package ithillel.ua.glovo;

import com.fasterxml.jackson.databind.ObjectMapper;
import ithillel.ua.glovo.model.Order;
import ithillel.ua.glovo.model.Product;
import ithillel.ua.glovo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

    @BeforeEach
    public void setUp() {
        order = new Order();
        order.setId(1L);
        order.setProducts(new ArrayList<>());
        order.setStatus("new");

        Mockito.when(orderService.getOrderById(anyLong())).thenReturn(order);
        Mockito.when(orderService.addOrder(any(Order.class))).thenReturn(order);
        Mockito.when(orderService.updateOrder(anyLong(), any(Order.class))).thenReturn(order);
        Mockito.when(orderService.addProductToOrder(anyLong(), any(Product.class))).thenReturn(order);
        Mockito.when(orderService.removeProductFromOrder(anyLong(), anyLong())).thenReturn(order);
        Mockito.when(orderService.deleteOrder(anyLong())).thenReturn(true);
    }

    @Test
    public void testGetOrder() throws Exception {
        mockMvc.perform(get("/orders/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("new"));
    }

    @Test
    public void testAddOrder() throws Exception {
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("new"));
    }

    @Test
    public void testUpdateOrder() throws Exception {
        order.setStatus("completed");

        mockMvc.perform(put("/orders/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("completed"));
    }

    @Test
    public void testAddProductToOrder() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(10.0);

        mockMvc.perform(patch("/orders/{orderId}/products", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products[0].name").value("Product 1"));
    }

    @Test
    public void testRemoveProductFromOrder() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product 1");
        product.setPrice(10.0);

        order.getProducts().add(product);

        mockMvc.perform(delete("/orders/{orderId}/products/{productId}", 1L, 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.products").isEmpty());
    }

    @Test
    public void testDeleteOrder() throws Exception {
        mockMvc.perform(delete("/orders/{id}", 1L))
                .andExpect(status().isOk());
    }
}