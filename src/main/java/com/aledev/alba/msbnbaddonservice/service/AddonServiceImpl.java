package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonMapper;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddonServiceImpl implements AddonService {
    private final AddonRepository addonRepository;
    private final AddonMapper mapper;

    @Override
    public List<AddonDto> saveAll(List<AddonDto> addons) {
        var addonList = addons.stream()
                .map(mapper::dtoToEntity)
                .collect(Collectors.toList());

        return addonRepository.saveAll(addonList)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddonDto> getAddonsByCategory(AddonCategory category) {
        return addonRepository.findAllByCategory(category)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AddonDto> getAddonsByType(AddonType type) {
        return addonRepository.findAllByType(type)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddonDto saveOne(AddonDto addonDto) {
        Addon savedAddon = addonRepository.save(mapper.dtoToEntity(addonDto));
        return mapper.entityToDto(savedAddon);
    }

    @Override
    public AddonDto updateAddon(AddonDto addonDto) {
        var addonToUpdate = mapper.dtoToEntity(addonDto);
        var updatedAddon = addonRepository.findById(addonToUpdate.getId())
                .orElseThrow(RuntimeException::new);
        return mapper.entityToDto(updatedAddon);
    }

    @Override
    public AddonDto getAddonById(Long id) {
        var addon = addonRepository.findById(id)
                .orElseThrow(RuntimeException::new);
        return mapper.entityToDto(addon);
    }

    @Override
    public void deleteAddon(Long id) {
        addonRepository.findById(id).ifPresent(addonRepository::delete);
    }
}
