package com.aledev.alba.msbnbaddonservice.service;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.entity.AddonOrder;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.repository.AddonOrderRepository;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonMapper;
import com.aledev.alba.msbnbaddonservice.web.mappers.AddonOrderMapperImpl;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import com.aledev.alba.msbnbaddonservice.web.model.Extra;
import com.aledev.alba.msbnbaddonservice.web.model.exception.AddonOrderException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AddonOrderServiceImplTest {
    @Mock
    AddonMapper addonMapper;

    @Mock
    AddonOrderMapperImpl orderMapper;

    @Mock
    AddonOrderRepository orderRepository;

    @Captor
    ArgumentCaptor<AddonOrder> captor;

    @InjectMocks
    AddonOrderServiceImpl addonService;

    AddonOrderDto orderDto;

    AddonOrder order;

    @BeforeEach
    void setUp() {
        orderDto = AddonOrderDto.builder()
                .totalPrice(BigDecimal.TEN)
                .paid(false)
                .qty((short)10)
                .bookingUid(UUID.randomUUID())
                .addon(AddonDto.builder()
                        .pricePerUnit(BigDecimal.ONE)
                        .type(AddonType.ORANGE_JUICE)
                        .category(AddonCategory.BREAKFAST)
                        .build())
                .build();

        order = AddonOrder.builder()
                .totalPrice(BigDecimal.TEN)
                .paid(false)
                .qty((short)10)
                .bookingUid(UUID.randomUUID())
                .addon(Addon.builder()
                        .pricePerUnit(BigDecimal.ONE)
                        .type(AddonType.ORANGE_JUICE)
                        .category(AddonCategory.BREAKFAST)
                        .build())
                .build();
    }

    @Test
    void testGetAddon_ReturnsDto() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderMapper.entityToDto(any())).thenReturn(orderDto);

        AddonOrderDto fetchedOrder = addonService.getAddonOrder(1L);

        verify(orderRepository, times(1)).findById(anyLong());
        assertNotNull(fetchedOrder);
        assertThat(fetchedOrder.getTotalPrice()).isEqualTo(orderDto.getTotalPrice());
    }

    @Test
    void testGetAddonNotFound_ThrowsException() {
        assertThrows(AddonOrderException.class, () -> addonService.getAddonOrder(12L));
    }

    @Test
    void testGetAddonsForBookingUuid_OneExtraPaidAndOneNotPaid_ReturnsExtraWithPaidFalse() {
        List<AddonOrder> addonOrderList = List.of(order, AddonOrder.builder()
                .totalPrice(BigDecimal.TEN)
                .paid(true)
                .qty((short)10)
                .bookingUid(UUID.randomUUID())
                .addon(Addon.builder()
                        .pricePerUnit(BigDecimal.ONE)
                        .type(AddonType.ORANGE_JUICE)
                        .category(AddonCategory.BREAKFAST)
                        .build())
                .build());
        when(orderRepository.findAllByBookingUid(any(UUID.class))).thenReturn(addonOrderList);

        Extra extras = addonService.getAddonOrdersForBookingUuid(UUID.randomUUID());

        assertThat(extras.getAddonList()).hasSize(addonOrderList.size());
        assertFalse(extras.getIsPaid());
    }

    @Test
    void testGetAddonsForBookingUuid_ExtrasAllPaid_ReturnsExtraWithPaidTrue() {
        AddonOrder order1 = AddonOrder.builder()
                .totalPrice(BigDecimal.TEN)
                .paid(true)
                .qty((short) 10)
                .bookingUid(UUID.randomUUID())
                .addon(Addon.builder()
                        .pricePerUnit(BigDecimal.ONE)
                        .type(AddonType.ORANGE_JUICE)
                        .category(AddonCategory.BREAKFAST)
                        .build())
                .build();
        List<AddonOrder> addonOrderList = List.of(order1, order1);
        when(orderRepository.findAllByBookingUid(any(UUID.class))).thenReturn(addonOrderList);

        Extra extras = addonService.getAddonOrdersForBookingUuid(UUID.randomUUID());

        assertThat(extras.getAddonList()).hasSize(addonOrderList.size());
        assertTrue(extras.getIsPaid());
    }

    @Test
    void testGetAddonsForBookingUuid_NotPaid_ReturnsExtraWithPaidFalse() {
        List<AddonOrder> addonOrderList = List.of(this.order, order);
        when(orderRepository.findAllByBookingUid(any(UUID.class))).thenReturn(addonOrderList);

        Extra extras = addonService.getAddonOrdersForBookingUuid(UUID.randomUUID());

        assertThat(extras.getAddonList()).hasSize(addonOrderList.size());
        assertFalse(extras.getIsPaid());
    }

    @Test
    void testSaveAllAddons() {
        List<AddonOrderDto> orderList = List.of(this.orderDto, orderDto);

        when(orderRepository.saveAll(anyList())).thenReturn(List.of(order, order));

        List<AddonOrderDto> savedOrders = addonService.saveAllAddonOrders(orderList);

        verify(orderRepository, times(1)).saveAll(anyList());
        assertThat(savedOrders).hasSize(2);
    }

    @Test
    void testCreateNewOrder() {
        orderDto.getAddon().setPricePerUnit(new BigDecimal("1.20"));
        orderDto.setQty((short)5);

        var expected = new BigDecimal("1.20").multiply(new BigDecimal(5));

        when(orderRepository.save(any(AddonOrder.class))).thenReturn(order);
        when(orderMapper.dtoToEntity(orderDto)).thenCallRealMethod();

        addonService.createNewOrder(orderDto);

        verify(orderRepository, times(1)).save(captor.capture());

        var capturedOrder = captor.getValue();

        assertThat(capturedOrder.getTotalPrice()).isEqualTo(expected);
    }

    @Test
    void testUpdateOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderRepository.save(any(AddonOrder.class))).thenReturn(order);

        addonService.updateOrder(1L, orderDto);

        verify(orderRepository, times(1)).save(any());
    }

    @Test
    void testUpdateOrder_NotFound_ThrowsException() {
        assertThrows(AddonOrderException.class, () -> addonService.updateOrder(1L, orderDto));
    }

    @Test
    void testUpdateOrderNotFound_ThrowsException() {
        assertThrows(AddonOrderException.class, () -> addonService.updateOrder(3L, orderDto));
    }

    @Test
    void testDeleteAddon_DeleteAddonWhenFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        addonService.deleteOrder(1L);

        verify(orderRepository, times(1)).delete(any());
    }

    @Test
    void testDeleteAddon_DoesNotDeleteAddonWhenNotFound() {
        addonService.deleteOrder(1L);

        verify(orderRepository, never() ).delete(any());
    }
}
