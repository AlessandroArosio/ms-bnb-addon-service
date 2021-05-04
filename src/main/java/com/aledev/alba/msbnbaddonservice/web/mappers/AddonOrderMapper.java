package com.aledev.alba.msbnbaddonservice.web.mappers;

import com.aledev.alba.msbnbaddonservice.domain.entity.AddonOrder;
import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import org.mapstruct.Mapper;

@Mapper
public interface AddonOrderMapper {

    AddonOrderDto entityToDto(AddonOrder addonOrder);
    AddonOrder dtoToEntity(AddonOrderDto dto);
}
