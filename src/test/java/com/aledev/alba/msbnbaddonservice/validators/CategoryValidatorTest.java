package com.aledev.alba.msbnbaddonservice.validators;

import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testAddonTypeMatchesCategory_returnSuccess() {
        AddonDto addonDto = AddonDto.builder()
                .pricePerUnit(BigDecimal.ONE)
                .category(AddonCategory.BREAKFAST)
                .type(AddonType.TOAST)
                .build();

        Set<ConstraintViolation<AddonDto>> violations = validator.validate(addonDto);
        assertThat(violations).isEmpty();
    }

    @Test
    void testAddonTypeDoesNotMatchCategory_returnError() {
        AddonDto addonDto = AddonDto.builder()
                .pricePerUnit(BigDecimal.ONE)
                .category(AddonCategory.BREAKFAST)
                .type(AddonType.DROP_OFF)
                .build();

        Set<ConstraintViolation<AddonDto>> violations = validator.validate(addonDto);
        assertThat(violations).hasSize(1);
    }
}
