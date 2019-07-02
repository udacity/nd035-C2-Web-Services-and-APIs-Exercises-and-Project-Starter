package com.udacity.security.repository;

import com.udacity.security.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
