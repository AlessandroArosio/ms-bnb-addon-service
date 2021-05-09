package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AddonServiceImplTest {
    @Mock
    AddonMapper mapper;

    @Mock
    AddonRepository repository;

    @InjectMocks
    AddonServiceImpl addonService;

    @Test
    void saveAll() {
    }

    @Test
    void getAddonsByCategory() {
    }

    @Test
    void getAddonsByType() {
    }

    @Test
    void saveOne() {
    }

    @Test
    void updateAddon() {
    }

    @Test
    void getAddonById() {
    }

    @Test
    void deleteAddon() {
    }
}
