package com.aledev.alba.msbnbaddonservice.web.controllers.api.v1;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.aledev.alba.msbnbaddonservice.service.AddonService;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddonControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    AddonController addonController;

    @Autowired
    AddonService addonService;

    @Autowired
    ObjectMapper objectMapper;

    private final static String API_ROOT = "/api/v1/addon";

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

    @Order(1)
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
                .andExpect(jsonPath("$", hasSize(7)))
                .andExpect(jsonPath("$[0].type", equalTo(AddonType.YOGURT.name())))
                .andExpect(jsonPath("$[1].type", equalTo(AddonType.ORANGE_JUICE.name())));
    }

    @Order(2)
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
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type", equalTo(AddonType.PICK_UP.name())));
    }

    @Order(3)
    @Test
    void testGetById_ReturnsAddonDto() throws Exception {
        mvc.perform(
                get(API_ROOT + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", equalTo("YOGURT")));
    }

    @Order(4)
    @Test
    void testSaveAll_ReturnsListDtoOfSavedAddons() throws Exception {
        List<AddonDto> addons = List.of(AddonDto.builder()
                        .category(AddonCategory.TAXI)
                        .type(AddonType.DROP_OFF)
                        .pricePerUnit(BigDecimal.TEN)
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.YOGURT)
                        .pricePerUnit(BigDecimal.ONE)
                        .build());

        String body = objectMapper.writeValueAsString(addons);

        mvc.perform(
                post(API_ROOT + "/addAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type", equalTo(AddonType.DROP_OFF.name())))
                .andExpect(jsonPath("$[1].type", equalTo(AddonType.YOGURT.name())));
    }

    @Order(5)
    @Test
    void testSaveAll_WithInvalidDto_ReturnsBadRequest() throws Exception {
        List<AddonDto> addons = List.of(AddonDto.builder()
                .category(AddonCategory.BREAKFAST)
                .type(AddonType.ORANGE_JUICE)
                .build());

        String body = objectMapper.writeValueAsString(addons);

        mvc.perform(
                post(API_ROOT + "/addAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON));
    }

    @Order(6)
    @Test
    void testSaveOneAddon_ReturnsDtoOfSavedAddon() throws Exception {
        var addon = AddonDto.builder()
                .category(AddonCategory.BREAKFAST)
                .type(AddonType.ORANGE_JUICE)
                .pricePerUnit(BigDecimal.ONE)
                .build();

        String body = objectMapper.writeValueAsString(addon);

        mvc.perform(
                post(API_ROOT + "/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.type", equalTo(AddonType.ORANGE_JUICE.name())));
    }

    @Order(7)
    @Test
    void testUpdateAddon_ReturnsDtoOfUpdatedAddon() throws Exception {
        AddonDto addon = addonService.getAddonById(1L);
        addon.setPricePerUnit(new BigDecimal("130.30").setScale(2, RoundingMode.HALF_UP));

        var body = objectMapper.writeValueAsString(addon);

        MvcResult mvcResult = mvc.perform(
                post(API_ROOT + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.pricePerUnit", equalTo(130.30)))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        AddonDto addonDto = objectMapper.readValue(content, AddonDto.class);

        assertThat(addonDto.getPricePerUnit()).isEqualTo(addon.getPricePerUnit());
    }

    @Order(8)
    @Test
    void testDeleteAddon_ReturnsStatusNoContent() throws Exception {
        mvc.perform(
                delete(API_ROOT + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
