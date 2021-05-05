package com.aledev.alba.msbnbaddonservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Extra {
    private List<BookingAddon> addonList;
    private Boolean isPaid;
}
