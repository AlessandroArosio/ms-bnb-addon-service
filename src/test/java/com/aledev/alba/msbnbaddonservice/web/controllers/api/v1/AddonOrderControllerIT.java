package com.aledev.alba.msbnbaddonservice.web.controllers.api.v1;

import com.aledev.alba.msbnbaddonservice.bootstrap.Bootstrap;
import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.entity.AddonOrder;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonOrderRepository;
import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.aledev.alba.msbnbaddonservice.service.AddonOrderService;
import com.aledev.alba.msbnbaddonservice.service.AddonService;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddonOrderControllerIT {

    @MockBean
    Bootstrap bootstrap;

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AddonService addonService;

    @Autowired
    AddonOrderService addonOrderService;

    @Autowired
    AddonOrderController controller;

    private static UUID bookingUUID;

    private final static String API_ROOT = "/api/v1/addonOrder";

    @BeforeAll
    static void beforeAll(@Autowired AddonRepository addonRepository,
                          @Autowired AddonOrderRepository orderRepository) {
        bookingUUID = UUID.randomUUID();

        List<Addon> addons = addonRepository.saveAll(List.of(
                Addon.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.ORANGE_JUICE)
                        .pricePerUnit(new BigDecimal("2.99"))
                        .build(),
                Addon.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.CROISSANT)
                        .pricePerUnit(new BigDecimal("1.20"))
                        .build(),
                Addon.builder()
                        .category(AddonCategory.TAXI)
                        .type(AddonType.PICK_UP)
                        .pricePerUnit(new BigDecimal("15.00"))
                        .build()));

        orderRepository.save(
                AddonOrder.builder()
                        .addon(addons.get(0))
                        .bookingUid(bookingUUID)
                        .uuid(UUID.randomUUID())
                        .paid(false)
                        .qty((short) 2)
                        .totalPrice(addons.get(0).getPricePerUnit().multiply(new BigDecimal(2)))
                        .build());
    }

    @Order(1)
    @Test
    void testGetAddonOrderById_ReturnsAddonOrderDto() throws Exception {
        mvc.perform(
                get(API_ROOT + "/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.addon.type", equalTo(AddonType.ORANGE_JUICE.name())))
                .andExpect(jsonPath("$.qty", equalTo(2)))
                .andExpect(jsonPath("$.addon.pricePerUnit", equalTo(2.99)))
                .andExpect(jsonPath("$.totalPrice", equalTo(5.98)));
    }

    @Order(2)
    @Test
    void testGetExtrasForBookingUUID_ReturnsExtras() throws Exception {
        mvc.perform(
                get(API_ROOT + "/extras/{uuid}", bookingUUID.toString()))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.addonList", hasSize(1)))
                .andExpect(jsonPath("$.addonList[0].category", equalTo(AddonCategory.BREAKFAST.name())));
    }

    @Order(3)
    @Test
    void testSaveAllAddonOrders_ReturnsListDto() throws Exception {
        List<AddonDto> addonDtos = addonService.saveAll(List.of(
                AddonDto.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.YOGURT)
                        .pricePerUnit(new BigDecimal("0.75"))
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.TAXI)
                        .type(AddonType.DROP_OFF)
                        .pricePerUnit(new BigDecimal("15.05"))
                        .build())
        );

        var addons = List.of(
                AddonOrderDto.builder()
                        .addon(addonDtos.get(0))
                        .qty((short) 7)
                        .paid(false)
                        .bookingUid(bookingUUID)
                        .build(),
                AddonOrderDto.builder()
                        .addon(addonDtos.get(1))
                        .qty((short) 2)
                        .paid(false)
                        .bookingUid(bookingUUID)
                        .build());

        var body = objectMapper.writeValueAsString(addons);

        mvc.perform(
                post(API_ROOT + "/addAll")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].totalPrice", equalTo(5.25)))
                .andExpect(jsonPath("$[0].addon.type", equalTo(AddonType.YOGURT.name())))
                .andExpect(jsonPath("$[0].addon.pricePerUnit", equalTo(0.75)));
    }

    @Order(4)
    @Test
    void testSaveNewOrder_ReturnsOrderDto() throws Exception {
        AddonDto addon = addonService.getAddonById(2L);

        var order = AddonOrderDto.builder()
                .paid(true)
                .addon(addon)
                .qty((short) 1)
                .bookingUid(bookingUUID)
                .build();

        var body = objectMapper.writeValueAsString(order);

        mvc.perform(
                post(API_ROOT + "/add")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.addon.pricePerUnit", equalTo(1.2)));
    }

    @Order(5)
    @Test
    void testUpdateOrder_ReturnsOrderDto() throws Exception {
        AddonOrderDto addonOrder = addonOrderService.getAddonOrder(1L);
        assertEquals((short) 2, addonOrder.getQty());

        addonOrder.setQty((short) 67);

        var body = objectMapper.writeValueAsString(addonOrder);

        mvc.perform(
                put(API_ROOT + "/{id}", addonOrder.getId())
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.addon.pricePerUnit", equalTo(2.99)))
                .andExpect(jsonPath("$.totalPrice", equalTo(200.33)));
    }

    @Order(6)
    @Test
    void testDeleteOrder_ReturnsHttpStatusNoContent() throws Exception {
        mvc.perform(
                delete(API_ROOT + "/1"))
                .andExpect(status().isNoContent());
    }
}
