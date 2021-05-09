package com.aledev.alba.msbnbaddonservice.validators;

import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CategoryValidator implements ConstraintValidator<ValidCategory, AddonDto> {

    @Override
    public void initialize(ValidCategory annotation) {
    }

    @Override
    public boolean isValid(AddonDto dto, ConstraintValidatorContext context) {
        if (dto.getCategory().equals(dto.getType().getCategory())) {
            return true;
        }
        return false;
    }
}
