package com.udacity.bootstrap.repository;

import com.udacity.bootstrap.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
