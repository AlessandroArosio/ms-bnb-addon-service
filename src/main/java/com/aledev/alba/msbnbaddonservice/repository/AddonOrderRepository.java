package com.aledev.alba.msbnbaddonservice.repository;

import com.aledev.alba.msbnbaddonservice.domain.entity.AddonOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AddonOrderRepository extends JpaRepository<AddonOrder, Long> {
    List<AddonOrder> findAllByBookingUid(UUID uuid);
}
