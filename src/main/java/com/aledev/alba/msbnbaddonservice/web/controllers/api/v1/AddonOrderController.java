package com.aledev.alba.msbnbaddonservice.web.controllers.api.v1;

import com.aledev.alba.msbnbaddonservice.service.AddonOrderService;
import com.aledev.alba.msbnbaddonservice.web.model.AddonOrderDto;
import com.aledev.alba.msbnbaddonservice.web.model.Extra;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addonOrder")
public class AddonOrderController {
    private final AddonOrderService addonOrderService;

    @GetMapping("/{id}")
    public ResponseEntity<AddonOrderDto> getAddonOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(addonOrderService.getAddonOrder(id), HttpStatus.OK);
    }

    @GetMapping("/extras/{uuid}")
    public ResponseEntity<Extra> getExtrasForBookingUUID(@PathVariable String uuid) {
        return new ResponseEntity<>(
                addonOrderService.getAddonOrdersForBookingUuid(UUID.fromString(uuid)),
                HttpStatus.OK);
    }

    @PostMapping("/addAll")
    public ResponseEntity<List<AddonOrderDto>> saveAllAddonOrders(@RequestBody @Valid List<AddonOrderDto> addons) {
        return new ResponseEntity<>(addonOrderService.saveAllAddonOrders(addons), HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public ResponseEntity<AddonOrderDto> saveNewOrder(@RequestBody @Valid AddonOrderDto dto) {
        return new ResponseEntity<>(addonOrderService.createNewOrder(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddonOrderDto> updateOrder(@PathVariable Long id,
                                                     @RequestBody @Valid AddonOrderDto dto) {
        return new ResponseEntity<>(addonOrderService.updateOrder(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long id) {
        addonOrderService.deleteOrder(id);
    }
}
