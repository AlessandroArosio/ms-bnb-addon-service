package com.aledev.alba.msbnbaddonservice.web.controllers.api.v1;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.entity.AddonOrder;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonOrderRepository;
import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddonOrderControllerIT {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AddonOrderController controller;

    private static UUID bookingUUID;

    private final static String API_ROOT = "api/v1/addonOrder";

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
                        .paid(false)
                        .qty((short)2)
                        .totalPrice(BigDecimal.ONE)
                        .build());
    }
}
