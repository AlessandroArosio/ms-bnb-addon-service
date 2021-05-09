package com.aledev.alba.msbnbaddonservice.validators;

import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CategoryValidator implements ConstraintValidator<ValidCategory, AddonDto> {

    @Override
    public boolean isValid(AddonDto dto, ConstraintValidatorContext context) {
        return Objects.equals(dto.getCategory(), dto.getType().getCategory());
    }
}
