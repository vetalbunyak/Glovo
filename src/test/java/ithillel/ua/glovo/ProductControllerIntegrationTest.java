package ithillel.ua.glovo;

import ithillel.ua.glovo.model.Product;
import ithillel.ua.glovo.repositories.ProductRepository;
import jdk.internal.jimage.ImageReaderFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.nio.file.Path;

import static jdk.internal.jimage.ImageReaderFactory.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testAddProduct() throws Exception {
        String productJson = "{ \"name\": \"Product 1\", \"price\": 10.0 }";

        mockMvc.perform((RequestBuilder) post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(productJson)))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.name").value("Product 1"))
                .andExpect((ResultMatcher) jsonPath("$.price").value(10.0));
    }

    @Test
    public void testGetProduct() throws Exception {
        Product product = new Product();
        product.setName("Product 1");
        product.setPrice(10.0);
        Product savedProduct = productRepository.save(product);

        ResultActions resultActions = mockMvc.perform(get(Path.of("/products/" + savedProduct.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(10.0));
    }

}