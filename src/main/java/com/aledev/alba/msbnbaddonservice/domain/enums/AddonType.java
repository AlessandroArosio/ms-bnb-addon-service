package com.aledev.alba.msbnbaddonservice.domain.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AddonType {
    CROISSANT(AddonCategory.BREAKFAST),
    ORANGE_JUICE(AddonCategory.BREAKFAST),
    PAIN_AU_CHOCOLATE(AddonCategory.BREAKFAST),
    TOAST(AddonCategory.BREAKFAST),
    YOGURT(AddonCategory.BREAKFAST),

    DROP_OFF(AddonCategory.TAXI),
    PICK_UP(AddonCategory.TAXI);

    private final AddonCategory category;

    AddonType(AddonCategory category) {
        this.category = category;
    }

    public static List<AddonType> addonsByCategory(AddonCategory category) {
        return Arrays.stream(AddonType.values())
                .filter(type -> type.category.equals(category))
                .collect(Collectors.toList());
    }

    public AddonCategory getCategory() {
        return this.category;
    }
}
