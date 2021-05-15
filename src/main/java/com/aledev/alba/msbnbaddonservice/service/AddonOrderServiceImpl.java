package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.repository.AddonOrderRepository;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonMapper;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonOrderMapper;
import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import com.aledev.alba.msbnbaddonservice.web.model.BookingAddon;
import com.aledev.alba.msbnbaddonservice.web.model.Extra;
import com.aledev.alba.msbnbaddonservice.web.model.exception.AddonOrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddonOrderServiceImpl implements AddonOrderService {
    private final AddonOrderRepository orderRepository;
    private final AddonOrderMapper orderMapper;
    private final AddonMapper addonMapper;

    @Override
    public AddonOrderDto getAddonOrder(Long id) {
        var addonOrder = orderRepository.findById(id)
                .orElseThrow(() -> new AddonOrderException("Order not found with ID: " + id));
        return orderMapper.entityToDto(addonOrder);
    }

    @Override
    public Extra getAddonOrdersForBookingUuid(UUID bookingUUID) {
        var addonsOrderList = orderRepository.findAllByBookingUid(bookingUUID);

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
    public List<AddonOrderDto> saveAllAddonOrders(List<AddonOrderDto> addons) {
        var addonOrders = orderRepository.saveAll(addons.stream()
                .map(dto -> {
                    calculateTotalPrice(dto);
                    return orderMapper.dtoToEntity(dto);
                })
                .collect(Collectors.toList()));

        return addonOrders.stream()
                .map(orderMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddonOrderDto createNewOrder(AddonOrderDto dto) {
        calculateTotalPrice(dto);
        var addonOrder = orderRepository.save(orderMapper.dtoToEntity(dto));
        return orderMapper.entityToDto(addonOrder);
    }

    @Override
    public AddonOrderDto updateOrder(Long id, AddonOrderDto dto) {
        calculateTotalPrice(dto);
        var addonOrder = orderRepository.findById(id)
                .orElseThrow(() -> new AddonOrderException("Addon no found with ID: " + id));
        addonOrder.setAddon(addonMapper.dtoToEntity(dto.getAddon()));
        addonOrder.setPaid(dto.getPaid());
        addonOrder.setQty(dto.getQty());
        addonOrder.setTotalPrice(dto.getTotalPrice());

        var orderUpdated = orderRepository.save(addonOrder);

        return orderMapper.entityToDto(orderUpdated);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.findById(id)
                .ifPresent(orderRepository::delete);
    }

    private void calculateTotalPrice(AddonOrderDto dto) {
        BigDecimal total = dto.getAddon().getPricePerUnit().multiply(new BigDecimal((dto.getQty())));
        dto.setTotalPrice(total);
    }
}
