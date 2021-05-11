package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonRepository;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonMapper;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AddonServiceImplTest {
    @Mock
    AddonMapper mapper;

    @Mock
    AddonRepository repository;

    @InjectMocks
    AddonServiceImpl addonService;

    Addon addon;

    @BeforeEach
    void setUp() {
        addon = Addon.builder()
                .category(AddonCategory.BREAKFAST)
                .type(AddonType.TOAST)
                .build();
    }

    @Test
    void testSaveAll_PersistsAllAddonsInDatabase() {
        List<Addon> addons = List.of(new Addon());

        when(repository.saveAll(anyList())).thenReturn(addons);

        addonService.saveAll(List.of(new AddonDto()));

        verify(repository, times(1)).saveAll(anyList());
    }

    @Test
    void testGetAddonsByCategory_ReturnsListOfSameCategory() {
        List<Addon> addons = List.of(addon);

        when(repository.findAllByCategory(any())).thenReturn(addons);

        addonService.getAddonsByCategory(AddonCategory.BREAKFAST);

        verify(repository, times(1)).findAllByCategory(any());
    }

    @Test
    void testGetAddonsByType_ReturnsListOfSameType() {
        List<Addon> addons = List.of(addon);

        when(repository.findAllByCategory(any())).thenReturn(addons);

        addonService.getAddonsByType(AddonType.TOAST);

        verify(repository, times(1)).findAllByType(any());
    }

    @Test
    void testSaveOne_PersistsAddonInDb() {
        when(repository.save(any(Addon.class))).thenReturn(new Addon());

        addonService.saveOne(new AddonDto());

        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateAddon_SavesUpdatedAddon() {
        when(repository.save(any(Addon.class))).thenReturn(new Addon());
        when(repository.findById(anyLong())).thenReturn(Optional.of(addon));

        var dto = AddonDto.builder().id(1L).build();

        addonService.updateAddon(dto);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any());
        verifyNoMoreInteractions(repository);

        int interactionsWithMock = mockingDetails(repository).getInvocations().size();
        assertThat(interactionsWithMock).isEqualTo(2);
    }

    @Test
    void testUpdateAddon_NotFound_ThrowsException() {
        var dto = AddonDto.builder().id(1L).build();
        assertThrows(RuntimeException.class, () -> addonService.updateAddon(dto));
    }

    @Test
    void testGetAddonById_ReturnsAddon() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(addon));

        addonService.getAddonById(1L);

        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void testGetAddonById_NotFound_ThrowsException() {
        assertThrows(RuntimeException.class, () -> addonService.getAddonById(1L));
    }

    @Test
    void testDeleteAddon_AddonFound_InvokeDeleteMethodInRepository() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(addon));

        addonService.deleteAddon(1L);

        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void testDeleteAddon_AddonNotFound_DoesNotInvokeDeleteMethod() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        addonService.deleteAddon(1L);

        verify(repository, times(1)).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }
}
