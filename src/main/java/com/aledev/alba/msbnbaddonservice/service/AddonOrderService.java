package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import com.aledev.alba.msbnbaddonservice.web.model.Extra;

import java.util.List;
import java.util.UUID;

public interface AddonOrderService {
    AddonOrderDto getAddonOrder(Long id);

    Extra getAddonOrdersForBookingUuid(UUID bookingUUID);

    List<AddonOrderDto> saveAllAddonOrders(List<AddonOrderDto> addons);

    AddonOrderDto createNewOrder(AddonOrderDto dto);

    AddonOrderDto updateOrder(Long id, AddonOrderDto dto);

    void deleteOrder(Long id);
}
