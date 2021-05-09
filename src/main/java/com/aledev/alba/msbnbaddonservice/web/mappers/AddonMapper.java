package com.aledev.alba.msbnbaddonservice.web.mappers;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import org.mapstruct.Mapper;

@Mapper
public interface AddonMapper {
    AddonDto entityToDto(Addon addon);
    Addon dtoToEntity(AddonDto dto);
}
