package com.aledev.alba.msbnbaddonservice.web.controllers.api.v1;

import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import com.aledev.alba.msbnbaddonservice.service.AddonService;
import com.aledev.alba.msbnbaddonservice.web.model.AddonDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/addon")
public class AddonController {
    private final AddonService addonService;

    @PostMapping("/category")
    @Operation(summary = "Fetch all addons by category")
    public ResponseEntity<List<AddonDto>> getAllByCategory(@RequestBody AddonCategory category) {
        return new ResponseEntity<>(addonService.getAddonsByCategory(category), HttpStatus.OK);
    }

    @PostMapping("/type")
    @Operation(summary = "Fetch all addons by type")
    public ResponseEntity<List<AddonDto>> getAllByType(@RequestBody AddonType type) {
        return new ResponseEntity<>(addonService.getAddonsByType(type), HttpStatus.OK);
    }

    @PostMapping("/addAll")
    @Operation(summary = "Save new list of addons")
    public ResponseEntity<List<AddonDto>> saveAll(@RequestBody @Valid List<AddonDto> addons) {
        return new ResponseEntity<>(addonService.saveAll(addons), HttpStatus.CREATED);
    }

    @PostMapping("/add")
    @Operation(summary = "Save new addon")
    public ResponseEntity<AddonDto> saveOneAddon(@RequestBody @Valid AddonDto addon) {
        return new ResponseEntity<>(addonService.saveOne(addon), HttpStatus.CREATED);
    }

    @PostMapping("/update")
    @Operation(summary = "Update existing addon")
    public ResponseEntity<AddonDto> updateAddon(@RequestBody @Valid AddonDto addon) {
        return new ResponseEntity<>(addonService.updateAddon(addon), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get one addon by ID")
    public ResponseEntity<AddonDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(addonService.getAddonById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete one addon")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddon(@PathVariable Long id) {
        addonService.deleteAddon(id);
    }
}
