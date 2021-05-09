package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;

import java.util.List;

public interface AddonService {
    List<AddonDto> saveAll(List<AddonDto> addons);
    List<AddonDto> getAddonsByCategory(AddonCategory category);
    List<AddonDto> getAddonsByType(AddonType type);
    AddonDto saveOne(AddonDto addonDto);
    AddonDto updateAddon(AddonDto addonDto);
    AddonDto getAddonById(Long id);
    void deleteAddon(Long id);
}
