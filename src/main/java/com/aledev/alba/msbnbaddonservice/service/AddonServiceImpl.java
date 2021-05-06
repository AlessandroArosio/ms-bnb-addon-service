package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.repository.AddonOrderRepository;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonOrderMapper;
import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import com.aledev.alba.msbnbaddonservice.web.model.BookingAddon;
import com.aledev.alba.msbnbaddonservice.web.model.Extra;
import com.aledev.alba.msbnbaddonservice.web.model.exception.AddonOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddonServiceImpl implements AddonService {
    private final AddonOrderRepository repository;
    private final AddonOrderMapper mapper;

    @Override
    public AddonOrderDto getAddon(Long id) {
        var addonOrder = repository.findById(id).orElseThrow(() -> new AddonOrderException("Order not found with ID: " + id));
        return mapper.entityToDto(addonOrder);
    }

    @Override
    public Extra getAddonsForBookingUuid(UUID bookingUUID) {
        var addonsOrderList = repository.findAllByBookingUid(bookingUUID);

        return Extra.builder()
                .addonList(addonsOrderList.stream()
                        .map(order -> BookingAddon.builder()
                                .category(order.getAddon().getCategory())
                                .type(order.getAddon().getType())
                                .price(order.getTotalPrice())
                                .quantity(order.getQty().intValue())
                                .build())
                        .collect(Collectors.toList()))
                .isPaid(addonsOrderList.stream()
                        .allMatch(o -> Objects.equals(Boolean.TRUE, o.getPaid())))
                .build();
    }

    @Override
    public List<AddonOrderDto> saveAllAddons(List<AddonOrderDto> addons) {
        var addonOrders = repository.saveAll(addons.stream()
                .map(mapper::dtoToEntity)
                .collect(Collectors.toList()));
        return addonOrders.stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public AddonOrderDto createNewOrder(AddonOrderDto dto) {
        var addonOrder = repository.save(mapper.dtoToEntity(dto));
        return mapper.entityToDto(addonOrder);
    }

    @Override
    public AddonOrderDto updateOrder(Long id, AddonOrderDto dto) {
        var addonOrder = repository.findById(id).orElseThrow(() -> new AddonOrderException("Addon no found with ID: " + id));
        addonOrder.setAddon(dto.getAddon());
        addonOrder.setPaid(dto.getPaid());
        addonOrder.setQty(dto.getQty());
        addonOrder.setTotalPrice(dto.getTotalPrice());

        var orderUpdated = repository.save(addonOrder);

        return mapper.entityToDto(orderUpdated);
    }

    @Override
    public void deleteAddon(Long id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }
}
