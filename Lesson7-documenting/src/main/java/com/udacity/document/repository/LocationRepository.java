package com.udacity.document.repository;

import com.udacity.document.entity.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
