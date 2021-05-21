package com.aledev.alba.msbnbaddonservice.bootstrap;

import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.aledev.alba.msbnbaddonservice.service.AddonService;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final AddonService addonService;
    private final AddonRepository addonRepository;

    @Override
    public void run(String... args) throws Exception {
        if (addonRepository.count() == 0) {
            populateAddons();
        }
    }

    private void populateAddons() {
        List<AddonDto> addonDtos = addonService.saveAll(List.of(
                AddonDto.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.YOGURT)
                        .pricePerUnit(new BigDecimal("1.00"))
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.ORANGE_JUICE)
                        .pricePerUnit(new BigDecimal("1.50"))
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.CROISSANT)
                        .pricePerUnit(new BigDecimal("1.00"))
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.PAIN_AU_CHOCOLATE)
                        .pricePerUnit(new BigDecimal("1.50"))
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.BREAKFAST)
                        .type(AddonType.TOAST)
                        .pricePerUnit(new BigDecimal("2.00"))
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.TAXI)
                        .type(AddonType.PICK_UP)
                        .pricePerUnit(new BigDecimal("20.00"))
                        .build(),
                AddonDto.builder()
                        .category(AddonCategory.TAXI)
                        .type(AddonType.DROP_OFF)
                        .pricePerUnit(new BigDecimal("1.50"))
                        .build()));
    }
}
