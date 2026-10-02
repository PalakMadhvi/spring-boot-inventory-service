package com.example.inventory;

import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

    @Autowired TestRestTemplate rest;
    @Autowired ProductRepository repo;

    @Test
    void createAndReadProduct() {
        var request = new java.util.HashMap<String, Object>();
        request.put("sku", "PHONE-001");
        request.put("name", "Demo Phone");
        request.put("description", "Integration test product");
        request.put("price", 19999.00);
        request.put("reorderLevel", 5);
        request.put("active", true);

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("admin", "test123");
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = rest.exchange(
                "/api/products", HttpMethod.POST,
                new HttpEntity<>(request, headers), String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(repo.findBySkuIgnoreCase("PHONE-001")).isPresent();
    }
}
