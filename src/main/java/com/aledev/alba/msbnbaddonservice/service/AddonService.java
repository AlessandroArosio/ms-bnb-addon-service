package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import com.aledev.alba.msbnbaddonservice.web.model.Extra;

import java.util.List;
import java.util.UUID;

public interface AddonService {
    AddonOrderDto getAddon(Long id);

    Extra getAddonsForBookingUuid(UUID bookingUUID);

    List<AddonOrderDto> saveAllAddons(List<AddonOrderDto> addons);

    AddonOrderDto createNewOrder(AddonOrderDto dto);

    AddonOrderDto updateOrder(Long id, AddonOrderDto dto);

    void deleteAddon(Long id);
}
