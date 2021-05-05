package com.aledev.alba.msbnbaddonservice.web.model;

import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingAddon {
    private AddonCategory category;
    private AddonType type;
    private BigDecimal price;
    private Integer quantity;
}
