package com.aledev.alba.msbnbaddonservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AddonOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    @NotNull
    private UUID bookingUid;

    @NotNull
    private Short qty;

    @OneToOne(targetEntity = Addon.class)
    private Addon addon;

    @NotNull
    @Positive
    private BigDecimal totalPrice;

    @NotNull
    private Boolean paid;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp orderDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Version
    private Long version;
}
