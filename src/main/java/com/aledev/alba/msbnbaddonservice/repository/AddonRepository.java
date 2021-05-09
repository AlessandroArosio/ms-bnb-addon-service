package com.aledev.alba.msbnbaddonservice.repository;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonCategory;
import com.aledev.alba.msbnbaddonservice.domain.enums.AddonType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddonRepository extends JpaRepository<Addon, Long> {
    List<Addon> findAllByCategory(AddonCategory category);
    List<Addon> findAllByType(AddonType type);
}
