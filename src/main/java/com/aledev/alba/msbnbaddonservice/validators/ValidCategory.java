package com.aledev.alba.msbnbaddonservice.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE, FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = CategoryValidator.class)
public @interface ValidCategory {
    String message() default "Invalid category for this addon";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
