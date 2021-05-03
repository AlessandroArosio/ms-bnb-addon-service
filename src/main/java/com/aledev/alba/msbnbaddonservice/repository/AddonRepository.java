package com.aledev.alba.msbnbaddonservice.repository;

import com.aledev.alba.msbnbaddonservice.domain.entity.Addon;
import org.springframework.data.repository.CrudRepository;

public interface AddonRepository extends CrudRepository<Long, Addon> {
}
