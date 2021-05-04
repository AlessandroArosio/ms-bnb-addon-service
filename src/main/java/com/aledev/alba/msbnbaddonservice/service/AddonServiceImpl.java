package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.repository.AddonOrderRepository;
import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddonServiceImpl implements AddonService {
    private final AddonOrderRepository repository;

    @Override
    public AddonOrderDto getAddon(Long id) {
        return null;
    }

    @Override
    public List<AddonOrderDto> getAddonsForBookingUuid(UUID bookingUUID) {
        return null;
    }

    @Override
    public List<AddonOrderDto> getAddonsByCategory(Long id) {
        return null;
    }

    @Override
    public List<AddonOrderDto> saveAllAddons(List<AddonOrderDto> addons) {
        return null;
    }

    @Override
    public AddonOrderDto createNewOrder(AddonOrderDto dto) {
        return null;
    }

    @Override
    public AddonOrderDto updateOrder(Long id, AddonOrderDto dto) {
        return null;
    }

    @Override
    public void deleteAddon(Long id) {

    }
}
