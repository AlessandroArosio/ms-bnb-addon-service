package com.aledev.alba.msbnbaddonservice.web.controllers.api.v1;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AddonControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    AddonController addonController;

    @Autowired
    ObjectMapper objectMapper;

    final static String API_ROOT = "/api/v1/addon";

    @BeforeAll
    static void beforeAll(@Autowired AddonRepository repository) {
        repository.saveAll(List.of(
                Addon.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.TOAST)
                        .pricePerUnit(new BigDecimal("0.99"))
                        .build(),
                Addon.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.PAIN_AU_CHOCOLATE)
                        .pricePerUnit(new BigDecimal("2.50"))
                        .build(),
                Addon.builder()
                        .category(AddonCategory.TAXI)
                        .type(AddonType.PICK_UP)
                        .pricePerUnit(new BigDecimal("15.00"))
                        .build()));
    }

    @Test
    void testGetAllByCategory_ReturnsListOfSelectedCategory() throws Exception {
        var body = objectMapper.writeValueAsString(AddonCategory.BREAKFAST);

        mvc.perform(
                post(API_ROOT + "/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type", equalTo(AddonType.TOAST.name())))
                .andExpect(jsonPath("$[1].type", equalTo(AddonType.PAIN_AU_CHOCOLATE.name())));
    }

    @Test
    void testGetAllByType_ReturnsListOfSelectedType() throws Exception {
        var body = objectMapper.writeValueAsString(AddonType.PICK_UP);

        mvc.perform(
                post(API_ROOT + "/type")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type", equalTo(AddonType.PICK_UP.name())));
    }
}
