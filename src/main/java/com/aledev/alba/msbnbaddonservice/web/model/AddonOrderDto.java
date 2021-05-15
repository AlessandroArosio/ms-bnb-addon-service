package com.aledev.alba.msbnbaddonservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddonOrderDto {

    private Long id;

    private UUID uuid;

    @NotNull
    private UUID bookingUid;

    @NotNull
    private Short qty;

    @NotNull
    private AddonDto addon;

    @Positive
    private BigDecimal totalPrice;

    @NotNull
    private Boolean paid;

    private Timestamp orderDate;

    private Timestamp lastModifiedDate;
}
