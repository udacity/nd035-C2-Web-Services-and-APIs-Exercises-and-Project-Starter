package com.udacity.test.repository;

import com.udacity.test.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
