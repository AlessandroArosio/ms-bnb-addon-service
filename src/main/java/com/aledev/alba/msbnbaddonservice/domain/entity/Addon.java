package com.aledev.alba.msbnbaddonservice.domain.entity;

import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Addon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AddonCategory category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AddonType type;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerUnit;
}
